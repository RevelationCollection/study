package com.study.spring.cloud.hystrix.config;

import com.netflix.hystrix.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class CustomerCommand extends HystrixCommand<Object> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private RestTemplate template;

    public CustomerCommand(RestTemplate template) {
        super(
                Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("study-hystrix"))
                        .andCommandKey(HystrixCommandKey.Factory.asKey("HelloController"))
                        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("studyThreadPool"))
                        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                                .withExecutionTimeoutInMilliseconds(100)
                                .withCircuitBreakerSleepWindowInMilliseconds(5000))
                        .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                                .withCoreSize(1)
                                .withMaxQueueSize(2))
        );
        this.template = template;
    }

    @Override
    protected Object run() {
        //核心实现
        return template.getForObject("http://HELLOSERVER/random", String.class, "");
    }

    @Override
    protected Object getFallback() {
        //降级实现
        logger.info("降级了");
        return "降级了，返回降级";
    }
}
