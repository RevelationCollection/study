package com.study.rpc.client.net;

import com.study.rpc.registry.discovery.ServiceInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

public class NettyNetClinet  implements NetClient{
    private Logger log = LoggerFactory.getLogger(getClass());



    @Override
    public byte[] sendRequest(byte[] data, ServiceInfo serviceInfo) throws Throwable{
        SendHandler sendHandler = new SendHandler(data);
        InetSocketAddress address = serviceInfo.getAddress();
        EventLoopGroup workGroup = new NioEventLoopGroup(5);
        Bootstrap bootstrap = new Bootstrap();
        Channel channel = null;
        try {
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(sendHandler);
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(address);
            channel = channelFuture.channel();
            channelFuture.sync();
            return sendHandler.getRespMsg();
        } finally {
            channel.close();
            workGroup.shutdownGracefully();
        }
    }



    private class SendHandler extends ChannelInboundHandlerAdapter{

        private Logger log = LoggerFactory.getLogger(getClass());

        private CountDownLatch countDownLatch =null;
        private byte[] respMsg = null;
        private byte[] reqData = null;

        public SendHandler(byte[] reqData) {
            this.reqData = reqData;
            countDownLatch = new CountDownLatch(1);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            log.info("连接服务器成功："+ctx);
            ByteBuf buffer = Unpooled.buffer(reqData.length);
            buffer.writeBytes(reqData);
            log.info("客户端准备发送消息："+buffer);
            ctx.writeAndFlush(buffer);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("客户端接收到消息："+msg);
            ByteBuf byteBuf = (ByteBuf)msg;
            byte[] array = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(array);
            respMsg = array;
            countDownLatch.countDown();
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }

        public byte[] getRespMsg(){
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                log.error("error",e);
            }
            return respMsg;
        }
    }
}
