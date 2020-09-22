package com.study.spring.cloud.bus;

import com.study.spring.cloud.bus.config.MyBusEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    @RequestMapping("/")
    public String home() {
        return "Hello World";
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        BusProperties busProperties = context.getBean(BusProperties.class);
        context.publishEvent(new MyBusEvent("这是spring-cloud-bus",busProperties.getId()));
    }


}
