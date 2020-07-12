package com.study.test.b6_sentinel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("sentinel")
@ContextConfiguration("classpath:applicationContext.xml")
public class SentinelTests {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test(){
        int i = 0;
        while (true){
            i++;
            stringRedisTemplate.opsForValue().set("test-value",String.valueOf(i));
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
