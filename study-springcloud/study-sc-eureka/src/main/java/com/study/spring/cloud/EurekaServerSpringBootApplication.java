package com.study.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerSpringBootApplication.class, args);
    }
}
