package s.wxj.websockettest.nettysocket;

import android.os.Message;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.util.CharsetUtil;

public class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {

    private static final String TAG = "WebSocketClientHandler";

    private NettySocketHelper nettySocketHelper;
    private WebSocketClientHandshaker handShaker;
    private ChannelPromise handshakeFuture;

    public WebSocketClientHandler(NettySocketHelper nettySocketHelper) {
        this.nettySocketHelper = nettySocketHelper;
    }

    public void handlerAdded(ChannelHandlerContext ctx) {
        this.handshakeFuture = ctx.newPromise();
    }

    public void setHandShaker(WebSocketClientHandshaker handShaker) {
        this.handShaker = handShaker;
    }

    public ChannelFuture handshakeFuture() {
        return this.handshakeFuture;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 通道激活时，移除重连消息，并且将重连次数重置为 0
        nettySocketHelper.workHandler.removeMessages(NettySocketHelper.RECONNECT_MESSAGE);
        nettySocketHelper.resetAlreadyReconnectCount();
        nettySocketHelper.mainHandler.sendEmptyMessage(NettySocketHelper.ON_CHANNEL_ACTIVE);
    }


    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        FullHttpResponse response;
        if (!this.handShaker.isHandshakeComplete()) {
            try {
                response = (FullHttpResponse) msg;
                //握手协议返回，设置结束握手
                this.handShaker.finishHandshake(ctx.channel(), response);
                //设置成功
                this.handshakeFuture.setSuccess();
                // 握手成功后发送心跳
                nettySocketHelper.workHandler.sendEmptyMessage(NettySocketHelper.HEART_MESSAGE);
            } catch (WebSocketHandshakeException e) {
                FullHttpResponse res = (FullHttpResponse) msg;
                String errorMsg = String.format("WebSocket Client failed to connect,status:%s,reason:%s", res.status(), res.content().toString(CharsetUtil.UTF_8));
                this.handshakeFuture.setFailure(new Exception(errorMsg));
            }
        } else if (msg instanceof FullHttpResponse) {
            response = (FullHttpResponse) msg;
            throw new IllegalStateException("Unexpected FullHttpResponse (getStatus=" + response.status() + ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        } else {
            WebSocketFrame frame = (WebSocketFrame) msg;
            Message message = nettySocketHelper.mainHandler.obtainMessage();
            message.what = NettySocketHelper.ON_CHANNEL_READ;
            message.obj = frame.copy();
            nettySocketHelper.mainHandler.sendMessage(message);
        }
    }

    /**
     * 通道关闭
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        nettySocketHelper.mainHandler.sendEmptyMessage(NettySocketHelper.ON_CHANNEL_INACTIVE);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        nettySocketHelper.workHandler.removeMessages(NettySocketHelper.HEART_MESSAGE);
        nettySocketHelper.workHandler.sendEmptyMessage(NettySocketHelper.RECONNECT_MESSAGE);
        nettySocketHelper.mainHandler.sendEmptyMessage(NettySocketHelper.ON_CHANNEL_EXCEPTION);
    }
}

