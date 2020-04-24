package com.study.jdk.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NioClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1",8080));
        while (!socketChannel.finishConnect())
            Thread.yield();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入:");
        String line = scanner.nextLine();
        ByteBuffer buffer = ByteBuffer.wrap(line.getBytes());
        while (buffer.hasRemaining()){
            socketChannel.write(buffer);
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (socketChannel.isOpen() && socketChannel.read(byteBuffer)!=-1){
            //长连接情况下，需要手动盘面的数据尤美有读取结束。
            if(byteBuffer.position()>0){
                break;
            }
        }
        System.out.println("收到服务端响应");
        byteBuffer.flip();
//        byte[] content = byteBuffer.array();
        byte[] content = new byte[byteBuffer.limit()];
        byteBuffer.get(content);
        System.out.println(new String(content));
        scanner.close();
        socketChannel.close();
    }
}
