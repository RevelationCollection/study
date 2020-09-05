package com.study.starter.custom.autoconfigure;

import com.study.starter.custom.bean.Girl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GirlProperties.class)
public class GirlAutoconfigura {

    @Bean
    public Girl getGirl(GirlProperties girlProperties){
        Girl girl = new Girl();
        girl.setFace(girlProperties.getFace());
        girl.setHight(girlProperties.getHight());
        girl.setName(girlProperties.getName());
        girl.setYear(girlProperties.getYear());
        return girl;
    }
}
