package com.study.spring.cloud.feign.custom.service;

import com.study.spring.cloud.feign.custom.annotation.CustomFeignGet;
import com.study.spring.cloud.feign.custom.annotation.CustomFeignClient;

@CustomFeignClient(baseurl = "http://www.baidu.com:80")
public interface CustomHelloService {

    @CustomFeignGet(url = "/index.htm")
    String customerTest();
}
