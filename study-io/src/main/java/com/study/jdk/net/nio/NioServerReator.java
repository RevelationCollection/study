package com.study.jdk.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class NioServerReator {
    //服务端
    private ServerSocketChannel serverSocketChannel;

    //1、创建多个线程 - accept处理reactor线程（accept线程）
    private  ReactorThread[] mainReactorThreads = new ReactorThread[1];
    //2、创建io线程 - 处理read事件，工作线程
    private  ReactorThread[] subReactorThreads = new ReactorThread[8];

    public static void main(String[] args) throws Exception{
        NioServerReator nioServerReator = new NioServerReator();
        nioServerReator.newGroup();//1、创建 main和sub两组线程
        nioServerReator.initAndRegister();//2、创建serverSocketChannel，注册到mianReactor线程上的selector上
        nioServerReator.bind();//3、为severScoketChannel绑定端口
    }

    public void newGroup() throws IOException{
        for (int i = 0; i < subReactorThreads.length; i++) {
            subReactorThreads[i] = new ReactorThread() {
                @Override
                public void handler(SelectableChannel channel) throws Exception {
                    //work 线程出负责处理read事件
                    SocketChannel clientChannel = (SocketChannel) channel;
                    NioServerSelector.readAndWrite(clientChannel);
                }
            };
        }

        for (int i = 0; i < mainReactorThreads.length; i++) {
            mainReactorThreads[i] = new ReactorThread() {
                AtomicInteger integer = new AtomicInteger(0);

                @Override
                public void handler(SelectableChannel channel) throws Exception {
                    //main 只负责处理acce[t事件
                    ServerSocketChannel server = (ServerSocketChannel) channel;
                    SocketChannel clientSocketChannel = server.accept();
                    clientSocketChannel.configureBlocking(false);

                    //不同---
                    int index = integer.getAndIncrement() % subReactorThreads.length;
                    ReactorThread workEventLoop = subReactorThreads[index];
                    workEventLoop.doStart();
                    SelectionKey selectionKey = workEventLoop.register(clientSocketChannel);
                    selectionKey.interestOps(SelectionKey.OP_READ);
                    System.out.println(Thread.currentThread().getName()+"收到新连接："+clientSocketChannel.getRemoteAddress());
                }
            };
        }
    }

    public void initAndRegister() throws Exception{
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        int index = new Random().nextInt(mainReactorThreads.length);
        ReactorThread eventLoop = mainReactorThreads[index];
        eventLoop.doStart();
        SelectionKey selectionKey = eventLoop.register(serverSocketChannel);
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);
    }

    public void bind() throws IOException{
        serverSocketChannel.bind(new InetSocketAddress(8080));
        System.out.println("服务器启动完成");
    }

    abstract static class ReactorThread extends Thread{

        Selector selector;

        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

        /**
         * selector 监听到事件后,调用这方法
         */
        public abstract void handler(SelectableChannel channel) throws Exception ;

        private ReactorThread() throws IOException {
            selector = Selector.open();
        }

        volatile boolean running = false;

        @Override
        public void run() {
            while (running){
                try {
                    Runnable task;
                    while ((task=queue.poll())!=null){
                        task.run();
                    }
                    selector.select(1000);
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        try {
                            iterator.remove();
                            int readyOps = key.readyOps();
                            if (((readyOps & (SelectionKey.OP_READ | SelectionKey.OP_ACCEPT))!=0 || readyOps==0)) {
                                SelectableChannel channel = (SelectableChannel) key.attachment();
                                channel.configureBlocking(false);
                                handler(channel);
                                if (!channel.isOpen()) {
                                    key.cancel();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            key.cancel();
                        }

                    }
                    selector.selectNow();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        public void doStart(){
            if (!running) {
                running=true;
                start();
            }
        }

        public SelectionKey register(SelectableChannel socketChannel) throws Exception {
            //为什么register要以任务提交的形式，让reactor去处理？
            //现在在执行channel注册到selector的过程中,会和调用seletor.select()方法的线程争用同一把锁
            //而select()方法是在eventLoop通过while调用。争抢概率搞，为了让register快一点，就用同一个线程处理
            FutureTask<SelectionKey> futureTask = new FutureTask<>(
                    () -> socketChannel.register(selector,0, socketChannel));
            queue.add(futureTask);
            return futureTask.get();
        }
    }

}
