package com.study.jdk.net.bio;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class BioServer {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("服务器启动成功");
        while (!serverSocket.isClosed()){
            try(Socket request = serverSocket.accept()){
                System.out.println("收到请求");
                InputStream inputStream = request.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                String msg ;
                while ((msg = reader.readLine())!=null){
                    if (msg.length()==0)
                        break;
                    System.out.println(msg);
                }
                System.out.println( "收到的数据是："+request.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        serverSocket.close();
    }

}
