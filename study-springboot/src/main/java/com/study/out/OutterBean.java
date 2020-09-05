package com.study.out;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OutterBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public void printOut(){
        logger.info("外部bean被加载");
    }
}
