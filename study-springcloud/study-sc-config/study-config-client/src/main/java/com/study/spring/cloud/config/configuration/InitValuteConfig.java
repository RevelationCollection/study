package com.study.spring.cloud.config.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitValuteConfig {
    @Value("${spring.application.name}")
    private String value;

    @Value("${custom.value}")
    private String customValue;
    @Value("${private.custom.value}")
    private String priCustomValue;

    @Bean
    public String customTestValue(){
        System.out.println(" name :"+value);
        System.out.println(" custom value :"+customValue);
        System.out.println(" private custom value :"+priCustomValue);
        return "";
    }
}
