package com.study.redis.b5_replication;

import io.lettuce.core.ReadFrom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@Profile("replication-rw")
public class ReplicationRwRedisAppConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(){
        System.out.println("使用读写分离");
        RedisStandaloneConfiguration serverConfig
                = new RedisStandaloneConfiguration("192.168.0.109",6379);
        //读写分离
        LettuceClientConfiguration clientConfig =
                LettuceClientConfiguration.builder().readFrom(ReadFrom.SLAVE).build();
        return new LettuceConnectionFactory(serverConfig,clientConfig);
    }
}
