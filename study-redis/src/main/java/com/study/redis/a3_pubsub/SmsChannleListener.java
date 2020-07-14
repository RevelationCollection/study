package com.study.redis.a3_pubsub;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
@Profile("pubsub")
public class SmsChannleListener {
    @Resource
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void setup(){
        RedisCallback<Object> redisCallback = new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.subscribe((message, pattern) -> {
                    System.out.println("收到消息--》" + message);
                }, PubsubRedisAppConig.TEST_TOPIC.getBytes());
                return null;
            }
        };
        redisTemplate.execute(redisCallback);
    }

}
