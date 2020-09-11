package com.study.spring.cloud.ribbon.web;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Profile("cloud-ribbon")
@RestController
public class CloudConsumerController {

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("test")
    public Object getTest() {
        return restTemplate.getForObject("http://HELLOSERVER/", String.class, "");
    }
}
