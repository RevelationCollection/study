package com.study.jdk.net.netty.simple.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class WebSocketServer {

    private static int PORT  = 9000;

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_REUSEADDR,true)
                    .childHandler(new WebSocketServerInitializer())
                    .childOption(ChannelOption.SO_REUSEADDR,true);
//            for (int i = 0; i < 1; i++) {
//                ChannelFuture channelFuture = bootstrap.bind(++PORT).addListener(new ChannelFutureListener() {
//                    public void operationComplete(ChannelFuture future) throws Exception {
//                            System.out.println("端口绑定完成：" + future.channel().localAddress());
//                    }
//                });
//            }
            // 端口绑定完成，启动消息随机推送(测试)
//            TestCenter.startTest();
            ChannelFuture future = bootstrap.bind(PORT).sync();
            System.out.println("服务器启动成功");
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
