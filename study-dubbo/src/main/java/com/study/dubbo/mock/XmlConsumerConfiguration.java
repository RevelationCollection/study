package com.study.dubbo.mock;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlConsumerConfiguration {

    public static void main(String[] args) {
        //-Djava.net.preferIPv4Stack=true
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("xml/dubbo-consumer.xml");
        context.start();
        BarService barService =  context.getBean(BarService.class); // 获取远程服务代理

        System.out.println(barService.testMockFunction("test mock"));
        context.close();
    }
}
