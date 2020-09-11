package com.study.spring.cloud.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ServerSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerSpringBootApplication.class, args);
    }

}
