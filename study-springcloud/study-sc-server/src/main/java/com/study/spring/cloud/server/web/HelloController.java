package com.study.spring.cloud.server.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class HelloController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("")
    public String index() {
        return "Hello Server port:" + serverPort;
    }

    @RequestMapping("random")
    public String randomTimeOut() {
        int sleep = new Random().nextInt(150);
        logger.info("本次休眠时间:" + sleep);
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            logger.error("sleep error", e);
        }
        return "Hello Server random TimeOut port:" + serverPort;
    }

    @RequestMapping("getValue")
    public String getValue(String str) {
        return "str:"+str+",Hello Server port:" + serverPort;
    }

    @RequestMapping("{path}")
    public String testChange(@PathVariable String path, @RequestBody String body){
        logger.info("path:{} ,body:{}",path,body);
        return "success port:" + serverPort;
    }
}
