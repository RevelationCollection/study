package com.study.jdk.net.netty.simple.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.LongAdder;

public class WebSocketServerHandler  extends SimpleChannelInboundHandler<Object> {

    private static final String WEBSOCKET_PATH = "/webscoket";

    private WebSocketServerHandshaker handshaker;

    public static final LongAdder counter = new LongAdder();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if(msg instanceof FullHttpRequest){
            counter.add(1L);
            handleHttpReuest(ctx,(FullHttpRequest)msg);
        }else if(msg instanceof WebSocketFrame){
            handleWebSocketFrame(ctx,(WebSocketFrame)msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("断开连接");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof CloseWebSocketFrame){
            Object userId = ctx.channel().attr(AttributeKey.valueOf("userId")).get();
            TestCenter.removeConnection(userId);
            System.out.println("请求断开连接");
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
        }
        else if (frame instanceof PingWebSocketFrame){
            System.out.println("ping" + frame);
            ctx.write(new PongWebSocketFrame(frame.content().retain()));
        }
        else if (frame instanceof TextWebSocketFrame){
            TextWebSocketFrame test = (TextWebSocketFrame) frame;
            ctx.channel().writeAndFlush(new TextWebSocketFrame(test.text()+
                    "，欢迎使用Netty WebSocket服务， 现在时刻:"+System.currentTimeMillis()));
        }
        // 不处理二进制消息
        else if (frame instanceof BinaryWebSocketFrame) {
            // Echo the frame
            ctx.write(frame.retain());
        }
    }

    private void handleHttpReuest(ChannelHandlerContext ctx, FullHttpRequest req) {
        if(!req.decoderResult().isSuccess()
            || req.method() != HttpMethod.GET
            || (!"websocket".equals(req.headers().get("Upgrade")))){
            System.out.println("not webscoket request");
            sendHttpResoponse(ctx,req,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        //构造握手响应返回
        WebSocketServerHandshakerFactory handshakerFactory = new WebSocketServerHandshakerFactory(
                getWebSocketLocation(req), null, true, 5 * 1024 * 1024);
        handshaker = handshakerFactory.newHandshaker(req);
        if (handshaker==null){
            //版本不支持
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        }   else {
            handshaker.handshake(ctx.channel(),req);
        }

    }

    private String getWebSocketLocation(FullHttpRequest request){
        String location = request.headers().get(HttpHeaderNames.HOST)+WEBSOCKET_PATH;
        return "WS://"+location;
    }

    private void sendHttpResoponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse response) {
        if(response.status().code()!=200){
            ByteBuf byteBuf = Unpooled.copiedBuffer(response.status().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(byteBuf);
            byteBuf.release();
            HttpUtil.setContentLength(response,response.content().readableBytes());
        }
        ChannelFuture channelFuture = ctx.channel().writeAndFlush(response);
        if (!HttpUtil.isKeepAlive(req) || response.status().code()!=200){
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
