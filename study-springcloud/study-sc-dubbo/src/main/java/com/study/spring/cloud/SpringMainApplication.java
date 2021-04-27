package com.study.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import java.util.TimeZone;

@EnableFeignClients(basePackages={"com.study.spring.cloud.feign"})
@ComponentScan(basePackages = {"com.study.spring.cloud.config","com.study.spring.cloud"})
@SpringCloudApplication
public class SpringMainApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(SpringMainApplication.class,args);
    }
}
