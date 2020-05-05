package com.study.jdk.net.netty.simple.handle;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("收到数据："+byteBuf.toString());
        ByteBuf out = Unpooled.wrappedBuffer("server send msg".getBytes());
        ctx.write(out);
        ctx.writeAndFlush(byteBuf);
        System.out.println("对象数据:"+System.identityHashCode(this));
        ctx.pipeline().remove(EchoHandler.class);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)  {
        cause.printStackTrace();
        ctx.close();
    }
}
