package com.study.jdk.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServerSelector {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        //2、构建selector选择器
        Selector selector = Selector.open();
        //蒋severSocketChannel注册到selector
        SelectionKey configSelectorKey = serverSocketChannel.register(selector, 0, serverSocketChannel);
        //对serverSocketChannel上面的accept实际感兴趣（serverSocketChannelz只能支持accept操作）
        configSelectorKey.interestOps(SelectionKey.OP_ACCEPT);
        //绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));
        System.out.println("启动成功");

        while (serverSocketChannel.isOpen()){
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                try {
                    iterator.remove();
                    //关注accept 和 read 两个事件
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.attachment();
                        SocketChannel clientSocketChannel = server.accept();
                        clientSocketChannel.configureBlocking(false);
                        clientSocketChannel.register(selector,SelectionKey.OP_READ,clientSocketChannel);
                        System.out.println("收到新连接："+clientSocketChannel.getRemoteAddress());
                    }
                    if(key.isReadable()){
                        SocketChannel socketChannel = (SocketChannel) key.attachment();
                        readAndWrite(socketChannel);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    key.cancel();
                }
            }
            selector.selectNow();//阻塞直到有新的事件
        }
    }

    public   static void readAndWrite(SocketChannel socketChannel) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (socketChannel.isOpen() && socketChannel.read(byteBuffer)!=-1){
            //长连接情况下，需要手动盘面的数据尤美有读取结束。
            if(byteBuffer.position()>0){
                break;
            }
        }
        if (byteBuffer.position()==0)
            return ;
        byteBuffer.flip();
//        byte[] content = byteBuffer.array();
        byte[] content = new byte[byteBuffer.limit()];
        byteBuffer.get(content);
        System.out.println(new String(content));
        System.out.println(Thread.currentThread().getName()+"收到数据，来自："+socketChannel.getRemoteAddress());
        String response = "HTTP/1.1 200 OK\r\n"
                +"Content-Length: 11\r\n"
                +"\r\n"
                +"Hello World!\r\n";
        ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
        while (buffer.hasRemaining()){
            socketChannel.write(buffer);
        }
    }
}
