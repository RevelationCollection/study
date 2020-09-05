package com.study.springboot.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InnerBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public void printInner(){
        logger.info(" 下级 包路径 bean 被加载");
    }
}
