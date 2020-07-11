package com.study.test.a0_example;

import com.study.redis.a0_example.SpringCacheService;
import com.study.redis.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("single")
public class SpringCacheTests {

    @Autowired
    SpringCacheService springCacheService;

    private  String userId = "user1";
    @Test
    public  void springCachefind(){
        User user = springCacheService.findUserById(userId);
        System.out.println(user);
    }

    @Test
    public void springChcaeUpdate(){
        User user = springCacheService.findUserById(userId);
        user.setName("name-update");
        springCacheService.updateUser(user);
        System.out.println(user);
    }


    @Test
    public void springChcaeRemove(){
        User user = springCacheService.findUserById(userId);
        springCacheService.removeUserById(userId);
        System.out.println(user);
    }

}
