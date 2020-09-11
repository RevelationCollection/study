package com.study.spring.cloud.ribbon.web;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Profile("nocloud-ribbon")
@RestController
public class ConsumerController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancerClient loadBalancerClient;

    @RequestMapping("test")
    public Object getTest() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("hello-server");
        String ip = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        return restTemplate.getForObject("http://" + ip + ":" + port, String.class, "");
    }
}
