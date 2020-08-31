package com.study.dubbo.consumer;

import com.study.dubbo.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlConsumerConfiguration {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("xml/dubbo-consumer.xml");
        context.start();

        HelloService helloService = (HelloService) context.getBean("demoService"); // 获取远程服务代理
        String hello = helloService.testSay("Hello World"); // 执行远程方法
        System.out.println(hello); // 显示调用结果

        System.out.println();
        System.out.println(helloService);
        context.close();
    }
}
