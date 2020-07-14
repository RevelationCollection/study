package com.study.redis.b6_sentinel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@Profile("sentinel")
public class SentinelRedisAppConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(){
//        System.out.println("使用哨兵");
//        RedisSentinelConfiguration serverConfig = new RedisSentinelConfiguration()
//                .master("mymaster")
//                //哨兵的地址
//                .sentinel("192.168.0.109", 26380)
//                .sentinel("192.168.0.109", 26381)
//                .sentinel("192.168.0.109", 26382);
//        return new LettuceConnectionFactory(serverConfig);
        System.out.println("使用哨兵版本");
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master("mymaster")
                // 哨兵地址
                .sentinel("192.168.100.241", 26380)
                .sentinel("192.168.100.241", 26381)
                .sentinel("192.168.100.241", 26382);
        return new LettuceConnectionFactory(sentinelConfig);
    }

}
