package com.study.jdk.net.nio;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ByteBufDemo {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        System.out.println(String.format("初始化 容量：%s，位置：%s，限制：%s",byteBuffer.capacity()
            ,byteBuffer.position(),byteBuffer.limit()));
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 2);
        byteBuffer.put((byte) 3);
        System.out.println("array:"+Arrays.toString(byteBuffer.array()));
        System.out.println(String.format("写入3字节后 容量：%s，位置：%s，限制：%s",byteBuffer.capacity()
                ,byteBuffer.position(),byteBuffer.limit()));
        //开始读取数据
        byteBuffer.flip();
        byte b = byteBuffer.get();
        System.out.println(b);
        b = byteBuffer.get();
        System.out.println(b);
        System.out.println(String.format("读取2字节后 容量：%s，位置：%s，限制：%s",byteBuffer.capacity()
                ,byteBuffer.position(),byteBuffer.limit()));
        //读模式，只能写入1字节
        //clear清除整个缓冲区，compact方法仅清除已阅读数据，转为写模式
        byteBuffer.compact();
        byteBuffer.put((byte) 4);
        byteBuffer.put((byte) 5);
        byteBuffer.put((byte) 6);
        System.out.println(String.format("读最终 容量：%s，位置：%s，限制：%s",byteBuffer.capacity()
                ,byteBuffer.position(),byteBuffer.limit()));
        byte[] array = byteBuffer.array();
        System.out.println(Arrays.toString(array));

        //rewind() 重置postion为0
    }
}
