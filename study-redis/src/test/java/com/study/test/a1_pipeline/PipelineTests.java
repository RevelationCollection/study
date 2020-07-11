package com.study.test.a1_pipeline;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
//@ComponentScan("com.study")
@ActiveProfiles("pipeline")
@ContextConfiguration("classpath:applicationContext.xml")
public class PipelineTests {

    @Resource
    RedisTemplate redisTemplate;

    @Test
    public void test(){
        long time = System.currentTimeMillis();
        ListOperations opsForList = redisTemplate.opsForList();
        for (int i =0 ; i<10000;i++){
            opsForList.leftPush("list",i);
        }
        System.out.println("nomal time:"+(System.currentTimeMillis()-time));
        time = System.currentTimeMillis();
        RedisCallback<String> callback = new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                for (int i =0 ; i<10000;i++){
                    redisConnection.lPush("list-2".getBytes(), String.valueOf(i).getBytes());
                }
                return null;
            }
        };
        redisTemplate.executePipelined(callback);
        System.out.println("pipeline time:"+(System.currentTimeMillis()-time));

    }
}
