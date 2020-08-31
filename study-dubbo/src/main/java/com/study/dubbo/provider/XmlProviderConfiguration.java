package com.study.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class XmlProviderConfiguration {

    public static void main(String[] args) throws IOException {
        //系统中开启了IPV6协议 -Djava.net.preferIPv4Stack=true
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("xml/dubbo-provider.xml");
        context.start();
        System.in.read();
    }
}
