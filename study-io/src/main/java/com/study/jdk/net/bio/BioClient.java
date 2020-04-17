package com.study.jdk.net.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Scanner;

public class BioClient {

    private static Charset charset = Charset.forName("utf-8");

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",8080);
        OutputStream outputStream = socket.getOutputStream();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入:");
        String msg = scanner.nextLine();
        outputStream.write(msg.getBytes(charset));
        scanner.close();
        socket.close();
    }
}
