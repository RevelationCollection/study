package com.study.springboot.web;

import com.study.springboot.properties.CarProperties;
import com.study.springboot.properties.PrefixProperties;
import com.study.starter.custom.bean.Girl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private CarProperties carProperties;

    @Resource
    private PrefixProperties prefixProperties;

    @Resource
    private Environment environment;

    @Resource
    private Girl girl;

    @RequestMapping("")
    public String index(){
        return "Hello World Study!";
    }

    @RequestMapping("getCar")
    public CarProperties getCar(){
        logger.info("old :{}",carProperties);
        String property = environment.getProperty("car.name");
        return carProperties;
    }

    @RequestMapping("getPrefix")
    public PrefixProperties getPrefix(){
        return prefixProperties;
    }

    @RequestMapping("getGirlStarter")
    public Girl getGirlStarter(){
        return girl;
    }
}
