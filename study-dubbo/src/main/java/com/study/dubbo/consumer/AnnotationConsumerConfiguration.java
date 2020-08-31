package com.study.dubbo.consumer;


import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@EnableDubbo(scanBasePackages = "com.study.dubbo.consumer")
@PropertySource("classpath:/properties/dubbo-consumer.properties")
@ComponentScan("com.study.dubbo.consumer")
public class AnnotationConsumerConfiguration {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                AnnotationConsumerConfiguration.class);
        context.start();
        DemoAction demoAction = context.getBean(DemoAction.class);
        String response = demoAction.doSayHello("annotation dubbo consuomer");
        System.out.println(" ----->  response:"+response);
        context.close();
    }
}
