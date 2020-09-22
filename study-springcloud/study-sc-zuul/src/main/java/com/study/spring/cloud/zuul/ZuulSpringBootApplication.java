package com.study.spring.cloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient
//@EnableDiscoveryClient 作用和EnableEurekaClient一样
@EnableZuulProxy
public class ZuulSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulSpringBootApplication.class,args);
    }
}
