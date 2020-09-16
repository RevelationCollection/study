package com.study.spring.cloud.feign;

import com.study.spring.cloud.feign.custom.util.CustomerFeignRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableFeignClients
@Import(CustomerFeignRegister.class)
public class FeignSpringBootApplicaiton {

    public static void main(String[] args) {
        SpringApplication.run(FeignSpringBootApplicaiton.class, args);
    }
}
