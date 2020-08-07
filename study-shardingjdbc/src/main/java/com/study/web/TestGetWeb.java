package com.study.web;

import com.study.service.HostNameService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class TestGetWeb {
    @Resource
    private HostNameService hostNameService;

    @RequestMapping("getHostName")
    public String getHostName(){
       return hostNameService.getHostName();
    }
}
