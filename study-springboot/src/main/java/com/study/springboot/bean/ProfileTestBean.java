package com.study.springboot.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class ProfileTestBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public void printProfileTest(){
        logger.info(" test profile is active");
    }
}
