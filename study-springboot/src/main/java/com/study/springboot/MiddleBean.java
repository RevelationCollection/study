package com.study.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MiddleBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public void printMiddle(){
        logger.info("同级Bean被加载了");
    }
}
