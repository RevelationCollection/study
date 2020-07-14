package com.study.test.b8_custom;

import com.study.redis.b8_custom.CustomExampleService;
import com.study.redis.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("custom") // 设置profile
public class CustomCacheTests {

    @Autowired
    private CustomExampleService customExampleService;

    // get
    @Test
    public void springCacheTest() throws Exception {
        User user = customExampleService.findUserById("tony");
        System.out.println(user);
    }
}
