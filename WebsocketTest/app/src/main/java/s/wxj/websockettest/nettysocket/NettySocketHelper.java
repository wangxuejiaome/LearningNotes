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

    public void connect() {

        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) {
                        ChannelPipeline channelPipeline = socketChannel.pipeline();
                        channelPipeline.addLast(new HttpClientCodec(), new HttpObjectAggregator(1024 * 1024 * 10));
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