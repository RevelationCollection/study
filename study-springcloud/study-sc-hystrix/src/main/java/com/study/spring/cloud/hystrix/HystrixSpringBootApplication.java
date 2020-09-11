package com.study.spring.cloud.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@EnableCircuitBreaker
@SpringBootApplication
public class HystrixSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixSpringBootApplication.class, args);
    }
}
