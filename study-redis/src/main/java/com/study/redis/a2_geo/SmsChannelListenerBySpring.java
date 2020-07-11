package com.study.redis.a2_geo;

import com.study.redis.a3_pubsub.PubsubRedisAppConig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.Arrays;

@Profile("pubsub")
@Configuration
public class SmsChannelListenerBySpring {

    @Bean
    public RedisMessageListenerContainer smsMessageListener(RedisConnectionFactory redisConnectionFactory){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        SmsSendListener smsSendList = new SmsSendListener();
        container.addMessageListener(smsSendList, Arrays.asList(new ChannelTopic(PubsubRedisAppConig.TEST_TOPIC)));
        return container;
    }

    class SmsSendListener implements MessageListener{

        @Override
        public void onMessage(Message message, byte[] pattern) {

                System.out.println("借助spring容器收到消息：" + message);
        }
    }
}


