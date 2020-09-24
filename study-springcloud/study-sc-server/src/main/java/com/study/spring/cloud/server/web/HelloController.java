package com.study.spring.cloud.server.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@RestController
public class HelloController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("")
    public String index() {
        return "Hello Server port:" + serverPort;
    }

    @RequestMapping("random")
    public String randomTimeOut() {
        int sleep = new Random().nextInt(150);
        logger.info("本次休眠时间:" + sleep);
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            logger.error("sleep error", e);
        }
        return "Hello Server random TimeOut port:" + serverPort;
    }

    @RequestMapping("getValue")
    public String getValue(String str) {
        return "str:"+str+",Hello Server port:" + serverPort;
    }

    @RequestMapping("{path}")
    public String testChange(@PathVariable String path, @RequestBody String body){
        logger.info("path:{} ,body:{}",path,body);
        return "success port:" + serverPort;
    }
    @RequestMapping("/**")
    public String testAll(HttpServletRequest request){
        // /archeshein/supplyDemo/selectSupplyDemoEntites 获取uri
        String requestURI = request.getRequestURI();
        // /archeshein 获取项目名称
        String contextPath = request.getContextPath();
        // http 获取协议
        String scheme = request.getScheme();
        // localhost 获取ip
        String serverName = request.getServerName();
        // 9091 获取端口
        int serverPort = request.getServerPort();
        // http://localhost:9091/archeshein/supplyDemo/selectSupplyDemoEntites 获取全路径url
        StringBuffer requestURL = request.getRequestURL();
        // /supplyDemo/test 获取资源路径
        String servletPath = request.getServletPath();
        return "path error port:" + serverPort+",url:"+requestURL;
    }
}
