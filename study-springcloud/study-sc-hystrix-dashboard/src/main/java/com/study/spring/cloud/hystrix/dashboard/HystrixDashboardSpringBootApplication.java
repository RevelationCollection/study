package com.study.spring.cloud.hystrix.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashboardSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardSpringBootApplication.class,args);
    }
}
