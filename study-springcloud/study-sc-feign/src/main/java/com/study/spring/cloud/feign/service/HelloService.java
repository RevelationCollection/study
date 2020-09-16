package com.study.spring.cloud.feign.service;

import com.study.spring.cloud.feign.service.fallback.HystrixHelloServicFallback;
import feign.Body;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//要调用的服务端eureka的服务名称
@FeignClient(name="helloserver",fallback = HystrixHelloServicFallback.class)
public interface HelloService {

    @RequestMapping(value = "",method = RequestMethod.GET)
    String test();

    @RequestMapping(value = "random",method = RequestMethod.GET)
    String testRandom();

    @RequestMapping(value = "getValue",method = RequestMethod.POST)
    String getValue(@RequestParam("str") String param);

    //请求json格式 {"name":"{请求参数变量}"}
    @Body("%7B\"name\":\"{value}\"%7D")
    @RequestMapping(value = "testChange",method = RequestMethod.POST,headers = {"Content-Type=application/json"})
    String testBody(@Param("value")String name);
}
