package com.study.test.a3_pubsub;

import com.study.redis.a3_pubsub.PubsubRedisAppConig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("pubsub")
@ContextConfiguration("classpath:applicationContext.xml")
public class PubSubTests {
    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void testListen(){
        System.out.println("message start");
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //wait  5 second send msg
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                Long res = redisConnection.publish(PubsubRedisAppConig.TEST_TOPIC.getBytes(), "message test send".getBytes());
                return res;
            }
        });
    }

    @Test
    public void test2(){
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.subscribe((message, pattern) -> {
                    System.out.println("收到消息，使用redisTemplate收到的：" + message);
                }, "__keyevent@0__:del".getBytes());
                return null;
            }
        });

        redisTemplate.opsForValue().set("hkkkk", "tony");
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        redisTemplate.delete("hkkkk");
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
