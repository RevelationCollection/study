package com.study.jdk.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
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
                //未获取到新连接
                for (SocketChannel channel : channels) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    while (channel.isOpen() && channel.read(byteBuffer)!=-1){
                        //长连接情况下，需要手动盘面的数据尤美有读取结束。
                        if(byteBuffer.position()>0){
                            break;
                        }
                    }
                    if (byteBuffer.position()==0)
                        continue;
                    byteBuffer.flip();
                    byte[] content = byteBuffer.array();
                    System.out.println(new String(content));
                    System.out.println("收到数据，来自："+channel.getRemoteAddress());
                    String response = "HTTP/1.1 200 OK\r\n"
                            +"Content-Length: 11\r\n"
                            +"\r\n"
                            +"Hello World!\r\n";
                    ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
                    while (buffer.hasRemaining()){
                        channel.write(buffer);
                    }
                }
            }
        }
    }
}
