package com.study.spring.cloud.feign.service.fallback;

import com.study.spring.cloud.feign.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HystrixHelloServicFallback implements HelloService {

    @Override
    public String test() {
        return "异常，降级了";
    }

    @Override
    public String testRandom() {
        return "异常，降级了lelelel";
    }

    @Override
    public String getValue(String param) {
        return param+",异常降级";
    }

    @Override
    public String testBody(String name) {
        return "错误降级啦";
    }
}
