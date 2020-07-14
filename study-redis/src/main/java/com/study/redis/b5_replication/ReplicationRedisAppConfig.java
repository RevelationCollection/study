package com.study.redis.b5_replication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@Profile("replication")
public class ReplicationRedisAppConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(){
        System.out.println("使用单机版");
        RedisStandaloneConfiguration configuration
                = new RedisStandaloneConfiguration("192.168.0.109",6379);
        return new LettuceConnectionFactory(configuration);
    }
}
