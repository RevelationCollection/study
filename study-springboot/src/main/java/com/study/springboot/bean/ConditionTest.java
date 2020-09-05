package com.study.springboot.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    @ConditionalOnClass(name="org.springframework.data.redis.core.RedisTemplate")
    public Object testJDBC(){
        logger.info("代码用到了mysql");
        return new Object();
    }
}
