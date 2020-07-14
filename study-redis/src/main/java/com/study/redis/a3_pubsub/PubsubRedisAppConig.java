package com.study.redis.a3_pubsub;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@Profile("pubsub")
public class PubsubRedisAppConig {

    public static final String TEST_TOPIC = "sms_test";

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(){
        System.out.println("使用单机版");
        RedisStandaloneConfiguration configuration
                = new RedisStandaloneConfiguration("192.168.0.109",6379);
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

}
