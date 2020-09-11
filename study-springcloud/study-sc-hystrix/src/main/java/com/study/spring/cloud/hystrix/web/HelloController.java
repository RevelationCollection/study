package com.study.spring.cloud.hystrix.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.study.spring.cloud.hystrix.config.CustomerCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class HelloController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("test")
    public Object test() {
        return new CustomerCommand(restTemplate).execute();
    }

    @HystrixCommand(fallbackMethod = "callTimeoutFallback",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "1"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "2")
            }, commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")
    })
    @RequestMapping("testHystrix")
    public Object testHystrix() {
        return restTemplate.getForObject("http://HELLOSERVER/random", String.class, "");
    }

    public Object callTimeoutFallback() {
        logger.info("超时降级了");
        return "请求2，返回降级数据";
    }
}
