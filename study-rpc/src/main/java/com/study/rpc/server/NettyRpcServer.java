package com.study.rpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 服务端网络rpc处理
 */
public class NettyRpcServer extends BaseRpcServer {

    private Logger log = LoggerFactory.getLogger(getClass());

    private Channel channel;

    public NettyRpcServer(int port, String protocol, RequestHandler requestHandler){
        super(port,protocol,requestHandler);
    }

    @Override
    public void start() {
        //配置服务器
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ChannelRequestHandler());
                            pipeline.addLast(new ChannelOutHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            this.channel = channelFuture.channel();
            channel.closeFuture().sync();
        }catch (Exception e){
            log.error("error",e);
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    @Override
    public void stop() {
        this.channel.close();
    }

    private class ChannelRequestHandler extends ChannelInboundHandlerAdapter{

        private Logger log = LoggerFactory.getLogger(getClass());

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            log.info("连接成功："+ctx);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("服务端收到消息："+msg);
            ByteBuf byteBuf = (ByteBuf) msg;
            byte[] req = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(req);
            byte[] response = requestHandler.handleRequest(req);
            log.info("处理成功，反写数据："+response);
            ByteBuf buffer = Unpooled.buffer(response.length);
            buffer.writeBytes(response);
            ctx.write(buffer);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            log.error("errpr",cause);
            ctx.close();
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            log.info("连接关闭：{}",ctx);
            super.channelInactive(ctx);
        }
    }

    private class ChannelOutHandler extends ChannelOutboundHandlerAdapter{

        private Logger log = LoggerFactory.getLogger(getClass());


    }
}


