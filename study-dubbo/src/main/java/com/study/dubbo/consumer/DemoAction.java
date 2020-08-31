package com.study.dubbo.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.study.dubbo.HelloService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoAction {

	@Reference
	private HelloService helloService;

	@RequestMapping("/sayHello")
	public String doSayHello(String name) {
		return helloService.testSay(name);
	}
}
