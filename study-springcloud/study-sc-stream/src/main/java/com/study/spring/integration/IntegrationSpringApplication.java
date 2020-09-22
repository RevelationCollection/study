package com.study.spring.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

@SpringBootApplication
public class IntegrationSpringApplication {

    public static void main(String[] args){
        ConfigurableApplicationContext context = SpringApplication.run(IntegrationSpringApplication.class, args);
        //消息发送接收
        testAmqp(context);
    }


    private static void testAmqp(ConfigurableApplicationContext context){
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MessageChannel outboudChannel = (MessageChannel)context.getBean("amqpOutboundChannel");
        MessagingTemplate template = new MessagingTemplate();
        template.sendAndReceive(outboudChannel,new GenericMessage<>("来自output消息"));
    }
}
