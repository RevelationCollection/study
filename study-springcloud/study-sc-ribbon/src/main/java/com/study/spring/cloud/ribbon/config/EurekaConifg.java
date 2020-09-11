package com.study.spring.cloud.ribbon.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@Profile("nocloud-ribbon")
@Configuration
@RibbonClients(@RibbonClient(value = "hello-server"))
public class EurekaConifg {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    public IRule customRibbonRule() {
        //自定义规则
        return new RandomRule();
    }
}
