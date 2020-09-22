package com.study.spring.cloud.bus.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestCustomEvent {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //注解监听事件
        context.register(EventConfiguration.class);
        //实现类监听事件
        context.register(MyListener.class);
        context.refresh();
        ApplicationEventPublisher publisher = context;
        publisher.publishEvent(new MyApplicationEvent("开始测试event事件"));
    }
}
