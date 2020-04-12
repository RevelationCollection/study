package com.study.jdk.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class NioServerChannelList {

    /** 已建立连接集合 */
    private  static  List<SocketChannel> channels = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));
        System.out.println("启动成功");
        while (serverSocketChannel.isOpen()){
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel !=null){
                socketChannel.configureBlocking(false);
                System.out.println("收到新连接:"+socketChannel.getRemoteAddress());
                channels.add(socketChannel);
            }else{
                //未获取到新连接,读取list数据
                for (SocketChannel channel : channels) {
                    NioServerSelector.readAndWrite(channel);
                }
            }
        }
    }
}
