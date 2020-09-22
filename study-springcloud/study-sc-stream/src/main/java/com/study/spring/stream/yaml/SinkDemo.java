package com.study.spring.stream.yaml;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Conditional;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Conditional(YamlStreamCondition.class)
@Component
public class SinkDemo {

    @StreamListener(Sink.INPUT)
    public void onMessage(Message<?> message){
        System.out.println("收到数据："+message.getPayload().toString());
    }
}
