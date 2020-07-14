package com.study.redis.a0_example;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Profile("single")
@EnableCaching
public class SingleRedisAppConfig {

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
        //设置对象转换规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        return redisTemplate;
    }
//    @Bean
//    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory){
//        StringRedisTemplate redisTemplate = new StringRedisTemplate();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        return redisTemplate;
//    }




    @Bean("cacheManager")
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
        return redisCacheManager;
    }
}
