package com.study.spring.oauth.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("")
    public String hello(){
        log.info(" >>>>>>> hello world <<<<<<<");
        return "Hello World!Gateway";
    }
}
