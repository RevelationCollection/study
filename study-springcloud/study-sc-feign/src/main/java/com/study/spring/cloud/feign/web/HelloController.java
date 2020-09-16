package com.study.spring.cloud.feign.web;

import com.study.spring.cloud.feign.custom.service.CustomHelloService;
import com.study.spring.cloud.feign.service.HelloService;
import com.study.spring.cloud.feign.service.TestHelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private HelloService helloService;

    @Resource
    private CustomHelloService customHelloService;

    @Resource
    private TestHelloService testHelloService;

    @RequestMapping("/test")
    public Object test(){
        logger.info("obj:{}",helloService);
        return helloService.test();
    }

    @RequestMapping("/testCustom")
    public Object testCustom(){
        return customHelloService.customerTest();
    }

    @RequestMapping("/testRandom")
    public Object testRandom(){
        return helloService.testRandom();
    }

    @RequestMapping("/testDupRandom")
    public Object testDupRandom(){
        return testHelloService.testRandom();
    }

    @RequestMapping("/getValue")
    public Object getValue(@RequestParam("param") String param){
        return helloService.getValue(param);
    }

    @RequestMapping("/testBody")
    public Object testChange(@RequestParam("name") String name){
        return helloService.testBody(name);
    }
}
