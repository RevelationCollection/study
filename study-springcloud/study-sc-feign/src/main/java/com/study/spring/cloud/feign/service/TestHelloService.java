package com.study.spring.cloud.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

//contextId 解决相同服务名，不同业务接口
@FeignClient(name = "helloserver",contextId = "orderserver")
public interface TestHelloService {

    @RequestMapping("random")
    String testRandom();
}
