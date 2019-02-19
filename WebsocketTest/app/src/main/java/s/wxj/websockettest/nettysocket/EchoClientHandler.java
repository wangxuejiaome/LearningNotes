package s.wxj.websockettest.nettysocket;

import android.os.Message;
import android.util.Log;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;

public class EchoClientHandler extends SimpleChannelInboundHandler<String> {

    private static final String TAG = "EchoClientHandler";

    private NettySocketHelper nettySocketHelper;
    private WebSocketClientHandshaker handshaker;
    private ChannelPromise handshakeFuture;

    public EchoClientHandler(NettySocketHelper nettySocketHelper) {
        this.nettySocketHelper = nettySocketHelper;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.handshakeFuture = ctx.newPromise();
    }

    public ChannelFuture handshakeFuture() {
        return this.handshakeFuture;
    }

    public void setHandshaker(WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        nettySocketHelper.mainHandler.sendEmptyMessage(NettySocketHelper.ON_CHANNEL_ACTIVE);
        Log.d(TAG, "channelActive:==> Thread:" + Thread.currentThread());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        Log.d(TAG, "channelRead0: ===> " + msg + ",Thread:" + Thread.currentThread());
        Message message = nettySocketHelper.mainHandler.obtainMessage();
        message.what = NettySocketHelper.ON_CHANNEL_READ;
        message.obj = msg;
        nettySocketHelper.mainHandler.sendMessage(message);
    }

    /**
     * 通道关闭
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        nettySocketHelper.mainHandler.sendEmptyMessage(NettySocketHelper.ON_CHANNEL_INACTIVE);
        Log.d(TAG, "channelInactive:==> Thread:" + Thread.currentThread());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        nettySocketHelper.mainHandler.sendEmptyMessage(NettySocketHelper.ON_CHANNEL_EXCEPTION);
        Log.d(TAG, "exceptionCaught:==> Thread:" + Thread.currentThread());
    }
}
