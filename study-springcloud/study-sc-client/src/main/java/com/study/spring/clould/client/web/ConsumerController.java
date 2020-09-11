package com.study.spring.clould.client.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class ConsumerController {

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("test")
    public Object getTest() {
        return restTemplate.getForObject("http://HELLOSERVER/", String.class, "");
    }
}
