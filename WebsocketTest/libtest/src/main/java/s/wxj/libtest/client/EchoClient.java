package s.wxj.libtest.client;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class EchoClient {

    private final String host;
    private final int port;

    public EchoClient() {
        this(0);
    }

    public EchoClient(int port) {
        this("localhost", port);
    }

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void star() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
//                    .remoteAddress(new InetSocketAddress(this.host, this.port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("正在连接中.....");
                            //ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                            ch.pipeline().addLast(new EchoClientHandler());
                            //ch.pipeline().addLast(new ByteArrayEncoder());
                            //ch.pipeline().addLast(new ChunkedWriteHandler());
                        }
                    });
            //ChannelFuture channelFuture = bootstrap.connect().sync();
            final Channel mChannel = bootstrap.connect(host, port).sync().channel();
            System.out.println("服务端连接成功...");

            String sendContent = "客户端我第一次发数据给服务器\n";
            ByteBuf seneMsg = Unpooled.buffer(sendContent.length());
            seneMsg.writeBytes(sendContent.getBytes());

            mChannel.writeAndFlush(seneMsg).addListener(new GenericFutureListener() {
                @Override
                public void operationComplete(Future future) throws Exception {
//
                    System.out.println(" future.isSuccess()..." + future.isSuccess());


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (true){

                                try {
                                    TimeUnit.MILLISECONDS.sleep(2000);


                                    String sendContent = "客户端发送消息\n";
                                    ByteBuf seneMsg = Unpooled.buffer(sendContent.length());
                                    seneMsg.writeBytes(sendContent.getBytes());
                                    mChannel.writeAndFlush(seneMsg);

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }).start();

                }
            });

//            channelFuture.channel().closeFuture().sync();`
//            System.out.println("连接已关闭....");
        } finally {
            //group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        new EchoClient("127.0.0.1", 8888).star();
        //new EchoClient("121.40.165.18", 8800).star();
    }

}
