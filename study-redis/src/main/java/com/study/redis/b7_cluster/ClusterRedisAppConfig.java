package com.study.redis.b7_cluster;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.util.Arrays;

@Configuration
@Profile("cluster")
public class ClusterRedisAppConfig {

    @Bean
    public JedisConnectionFactory redisConnectionFactory(){
        System.out.println("load redis cluster 环境");
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(Arrays.asList(
                "192.168.0.109:6380",
                "192.168.0.109:6381",
                "192.168.0.109:6382",
                "192.168.0.109:6383"

        ));
        return new JedisConnectionFactory(clusterConfiguration);
    }
}
