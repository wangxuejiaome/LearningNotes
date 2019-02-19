package s.wxj.websockettest.nettysocket;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.net.URI;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


/**
 * 需求：
 * 1. 用户只需要传入 host 和 port 就可以连接上服务器；
 * 2. 用户在连接上服务的时候可以做操作；
 * 3. 用户可以发送消息；
 * 4. 用户可以接收到消息，并且在接收到消息后，可以做操作
 * 5. 连接失败了需要通知用户，用户可以做相应的操作
 * 6. 失败了系统可以支持自动重连；
 * 7. 用户可以主动关闭连接
 * 8. 发送心跳
 * <p>
 * 实现思路：
 * 1. 先将该类设计成单例模式
 * <p>
 * <p>
 * 疑问：
 * 1. 在 initChannel 时，mainHandler 的设置有先后顺序么？
 */
public class NettySocketHelper {

    private static final String TAG = "NettySocketHelper";

    /**
     * socket 连接成功
     */
    static final int ON_CHANNEL_ACTIVE = 1;
    /**
     * 客户端接收到数据
     */
    static final int ON_CHANNEL_READ = 2;
    /**
     * 连接断开
     */
    static final int ON_CHANNEL_INACTIVE = 3;
    /**
     * 连接异常
     */
    static final int ON_CHANNEL_EXCEPTION = 4;

    /**
     * 失败重连
     */
    static final int RECONNECT_MESSAGE = 5;
    /**
     * 心跳消息
     */
    static final int HEART_MESSAGE = 6;


    /**
     * 当用户同时提供 url 和 ip 端口方式连接时，优先使用 url 方式进行连接
     */
    private String socketUrl;
    private String host;
    private int port;
    private Channel channel;
    private long heartBeatInterval;
    private int maxRetryCount;
    /**
     * 已重连次数
     */
    private int alReadyConnectCount = 0;
    /**
     * 回调使用者接口处理
     */
    private Observer observer;

    /**
     * netty 线程，通过 mainHandler 切换到主线程，调用用户实现的接口，让用户实现自己的逻辑
     */
    Handler mainHandler;

    /**
     * 处理心跳和失败重连：
     * 1）通道激活时发送心跳，当关闭通道或异常重连时关闭心跳
     * 2）重连的触发时机:首次连接异常和通道中途异常时触发重连
     */
    Handler workHandler;


    private NettySocketHelper(Config config) {

        this.host = config.host;
        this.port = config.port;
        this.socketUrl = config.socketUrl;
        this.heartBeatInterval = config.heartBeatInterval;
        this.maxRetryCount = config.maxRetryCount;
        this.observer = config.observer;

        mainHandler = new Handler(Looper.getMainLooper(), mainHandlerCallback);
        HandlerThread workThread = new HandlerThread("OkHttpWebSocketHelper");
        workThread.start();
        workHandler = new Handler(workThread.getLooper(), workHandlerCallback);
    }


    /*ws://10.49.24.61:8083/push-agent/websocket*/
    /*ws://biuoscnsit-h2.cnsuning.com:80/push-agent/websocket*/
    // URI socketUrl = new URI("ws://10.49.24.61:8083/push-agent/websocket");
    // channel = bootstrap.connect(socketUrl.getHost(), socketUrl.getPort()).sync().channel();

    public void connect() {

        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
       /* bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));
                        ch.pipeline().addLast(new ChunkedWriteHandler());
                        if (channelHandler == null) {
                            ch.pipeline().addLast(new EchoClientHandler(NettySocketHelper.this));
                        } else {
                            ch.pipeline().addLast(channelHandler);
                        }
                    }
                });*/

        bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_BACKLOG, 1024 * 1024 * 10)
                .group(group)
                .handler(new LoggingHandler(LogLevel.INFO))
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) {
                        ChannelPipeline channelPipeline = socketChannel.pipeline();
                        channelPipeline.addLast(new HttpClientCodec(),
                                new HttpObjectAggregator(1024 * 1024 * 10));
                        channelPipeline.addLast("hookedHandler", new WebSocketClientHandler(NettySocketHelper.this));
                    }
                });

        try {
            URI socketURI;
            if (!TextUtils.isEmpty(socketUrl)) {
                socketURI = new URI(socketUrl);
            } else {
                socketURI = new URI("ws://" + host + ":" + port);
            }

            channel = bootstrap.connect(socketURI.getHost(), socketURI.getPort()).sync().channel();
            //进行握手
            HttpHeaders httpHeaders = new DefaultHttpHeaders();
            WebSocketClientHandshaker handShaker = WebSocketClientHandshakerFactory.newHandshaker(socketURI, WebSocketVersion.V13, null, true, httpHeaders);
            WebSocketClientHandler handler = (WebSocketClientHandler) channel.pipeline().get("hookedHandler");
            handler.setHandShaker(handShaker);
            handShaker.handshake(channel);
            //阻塞等待是否握手成功
            handler.handshakeFuture().sync();

           /* Map<String, String> requestMap = new HashMap<String, String>();
            requestMap.put("deviceId", "AI-BOX-13");
            requestMap.put("userAgent", "BiuOS-TV/1.0");
            requestMap.put("custNo", "12345");
            //JSONObject json =new JSONObject(requestMap);
            *//*{"deviceId":"w4e3454","userAgent":"BiuOS-TV/1.0","custNo":"4355878"}*//*
            TextWebSocketFrame frame = new TextWebSocketFrame("{\"deviceId\":\"w4e3454\",\"userAgent\":\"BiuOS-TV/1.0\",\"custNo\":\"4355878\"}");
            channel.writeAndFlush(frame).addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("text send success");
                    } else {
                        System.out.println("text send failed  " + channelFuture.cause().getMessage());
                    }
                }
            });*/


        } catch (Exception e) {
            reconnect();
            e.printStackTrace();
        }
    }

    private void reconnect() {
        workHandler.removeMessages(HEART_MESSAGE);
        if (alReadyConnectCount < maxRetryCount) {
            // 启动重连机制是: 1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒 ...
            long nextReconnectInterval = (long) Math.pow(2, alReadyConnectCount);
            Log.d(TAG, nextReconnectInterval + "秒后尝试第" + (alReadyConnectCount + 1) + "重连");
            workHandler.sendEmptyMessageDelayed(RECONNECT_MESSAGE, nextReconnectInterval * 1000);
            alReadyConnectCount++;
        }
    }

    void resetAlreadyReconnectCount() {
        alReadyConnectCount = 0;
    }

    public void sendMessage(final String message) {

        TextWebSocketFrame frame = new TextWebSocketFrame(message);
        channel.writeAndFlush(frame).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                if (future.isSuccess()) {
                    Log.d(TAG, "sendMessage:==> " + message);
                } else {
                    Log.d(TAG, "operationComplete: send message failed");
                }
            }
        });
    }

    public void disConnected() {
        if (channel != null) {
            channel.close();
        }
        mainHandler.removeCallbacksAndMessages(null);
        workHandler.removeCallbacksAndMessages(null);
    }


    /**
     * 参数配置
     */
    public static class Config {

        private String host = "";
        private int port = 80;
        private String socketUrl;
        private int heartBeatInterval = 2 * 1000;
        private int maxRetryCount = 10;
        private Observer observer;

        public Config host(String host) {
            this.host = host;
            return this;
        }

        public Config port(int port) {
            this.port = port;
            return this;
        }

        public Config socketUrl(String socketUrl) {
            this.socketUrl = socketUrl;
            return this;
        }

        public Config heartBeatInterval(int millisecond) {
            this.heartBeatInterval = millisecond;
            return this;
        }

        public Config maxRetryCount(int maxRetryCount) {
            this.maxRetryCount = maxRetryCount;
            return this;
        }

        /**
         * 设置回调处理用户逻辑
         */
        public Config setObserver(Observer observer) {
            this.observer = observer;
            return this;
        }

        public NettySocketHelper build() {
            return new NettySocketHelper(this);
        }
    }


    private Handler.Callback mainHandlerCallback = new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (observer == null) {
                return true;
            }
            int what = msg.what;
            switch (what) {
                case ON_CHANNEL_ACTIVE:
                    observer.onConnected();
                    break;
                case ON_CHANNEL_READ:
                    observer.onDataResponse((WebSocketFrame) msg.obj);
                    break;
                case ON_CHANNEL_INACTIVE:
                    observer.disConnected();
                    break;
                case ON_CHANNEL_EXCEPTION:
                    observer.onFailed();
                    break;
            }
            return true;
        }
    };


    private Handler.Callback workHandlerCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case RECONNECT_MESSAGE:
                    connect();
                    break;
                case HEART_MESSAGE:
                    sendMessage("{\"heartBeat\":\"heart bump\"}");
                    workHandler.sendEmptyMessageDelayed(HEART_MESSAGE, heartBeatInterval);
                    break;
            }
            return true;
        }
    };

    public interface Observer {

        void onConnected();

        /**
         * 接收 webSocketFrame 数据
         *
         * @param webSocketFrame 具体类型：TextWebSocketFrame、BinaryWebSocketFrame、
         *                       PongWebSocketFrame、PongWebSocketFrame、
         *                       ContinuationWebSocketFrame、CloseWebSocketFrame
         */
        void onDataResponse(WebSocketFrame webSocketFrame);

        void disConnected();

        void onFailed();
    }
}