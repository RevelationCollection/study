package com.study.jdk.net.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServerThreadPool {

    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("服务器启动成功");
        while (!serverSocket.isClosed()){
            Socket request = serverSocket.accept();
            pool.execute(()->{
                System.out.println("收到请求");
                try{
                    InputStream inputStream = request.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,  StandardCharsets.UTF_8));
                    String msg ;
                    while ((msg = reader.readLine())!=null){
                        if (msg.length()==0)
                            break;
                        System.out.println(msg);
                    }
                    System.out.println( "收到的数据是："+request.toString());
                    OutputStream outputStream = request.getOutputStream();
                    outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                    outputStream.write("Content-Length: 11\r\n".getBytes());
                    outputStream.write("\r\n".getBytes());
                    outputStream.write("Hello World!\r\n".getBytes());
                    outputStream.flush();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        request.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        serverSocket.close();
    }

}
