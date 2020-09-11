package com.study.springboot.actuator.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MyHealthCheck extends AbstractHealthIndicator {


    @Override
    protected void doHealthCheck(Health.Builder builder){
        Random random = new Random();
        int num = random.nextInt();
        if (num%2==0) {
            builder.withDetail("内容。。。。。","123").up();
        }else {
            builder.withDetail("内容2","456").down();
        }
    }
}
