package com.study.dubbo.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.study.dubbo.HelloService;

import java.util.UUID;

@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String testSay(String param) {
        String uuid = UUID.randomUUID().toString();
        System.out.println("     ---> param :"+param +",uuid:"+uuid);
        return uuid;
    }
}
