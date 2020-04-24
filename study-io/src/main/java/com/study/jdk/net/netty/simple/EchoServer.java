package com.study.jdk.net.netty.simple;

import com.study.jdk.net.netty.simple.handle.EchoHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer {

    public static void main(String[] args) {
        //Configure the sever
        EventLoopGroup accetpGroup = new NioEventLoopGroup(1);
        //work threads
        EventLoopGroup workGroup = new NioEventLoopGroup(3);

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(accetpGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel)  {
                            //每个连接独立的初始加载
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new EchoHandler());
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(8080).sync();
            System.out.println("服务器启动成功");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            accetpGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
