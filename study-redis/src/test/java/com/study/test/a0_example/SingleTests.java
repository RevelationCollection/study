package com.study.test.a0_example;

import com.study.redis.a0_example.SingleExampleService;
import com.study.redis.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("single") // 设置profile
public class SingleTests {

    @Resource
    SingleExampleService singleExampleService;

    @Test
    public  void setTest(){
        singleExampleService.setByCache("tony","");
        singleExampleService.setByCache("a","n");
        singleExampleService.setByCache("ccc","1211");
    }

    @Test
    public void getTest(){
        User user = singleExampleService.findUser("user1");
        System.out.println(user);
    }
}
