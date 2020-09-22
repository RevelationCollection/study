package com.study.spring.stream.yaml;

import com.study.spring.stream.converter.MyMessageConverter;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

@Conditional(YamlStreamCondition.class)
@EnableBinding({Sink.class, Source.class})
public class YamlStreamConfiguration {

    //定时发送
    @Bean
    @InboundChannelAdapter(value = Source.OUTPUT,poller = @Poller(fixedDelay = "1000",maxMessagesPerPoll = "1"))
    public MessageSource<String> sendMessage(){
        return ()-> new GenericMessage<>("hello , spring cloud stream integration");
    }

    @Bean
    public MyMessageConverter customerMessageConverter(){
        return new MyMessageConverter();
    }
}
