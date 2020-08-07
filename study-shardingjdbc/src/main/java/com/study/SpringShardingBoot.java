package com.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableAspectJAutoProxy
@MapperScan("com.study.dao")
@SpringBootApplication
public class SpringShardingBoot {

    public static void main(String[] args) {
        SpringApplication.run(SpringShardingBoot.class);
    }
}
