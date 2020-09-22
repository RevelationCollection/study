package com.study.spring.integration;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;


@Configuration
public class AppConfig {
    /** 队列名称*/
    private static final String QUEUE_NAME = "queue-test";

    // ---  接收消息 --- start
    @Bean
    public Queue queue(){
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public MessageChannel amqpInputChannel(){
        //为每个发送消息调用的单个订阅通道。调用是发生在发送方的线程中
        return new DirectChannel();
    }

    @Bean
    public AmqpInboundChannelAdapter inboundChannelAdapter(ConnectionFactory connectionFactory,
                                                           @Qualifier("amqpInputChannel") MessageChannel messageChannel){
        System.out.println("------  inboundChannelAdapter ---- 被加载了  -- ");
        //适配器
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        AmqpInboundChannelAdapter amqpInboundChannelAdapter = new AmqpInboundChannelAdapter(container);
        amqpInboundChannelAdapter.setOutputChannel(messageChannel);
        return amqpInboundChannelAdapter;
    }
    // ---  接收消息 --- end
    // ---  发送消息 --- start
    @Bean
    public MessageChannel amqpOutputChannel(){
        System.out.println("------  amqpOutputChannel ---- 被加载了  -- ");
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "amqpOutboundChannel")
    public AmqpOutboundEndpoint amqpOutbound(AmqpTemplate amqpTemplate){
        System.out.println("------  amqpOutbound ---- 被加载了  -- ");
        AmqpOutboundEndpoint outBound = new AmqpOutboundEndpoint(amqpTemplate);
        outBound.setRoutingKey(QUEUE_NAME);
        return outBound;
    }
    // ---  发送消息 --- end

    @Bean
    public String testBean(){
        System.out.println("------ ---- 我被加载了  -- ");
        return Boolean.TRUE.toString();
    }
}
