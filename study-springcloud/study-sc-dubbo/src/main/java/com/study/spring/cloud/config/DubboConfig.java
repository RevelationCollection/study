package com.study.spring.cloud.config;

import com.study.spring.cloud.feign.RemoteFundInService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboConfig {

//  @DubboReference(version = "1.0.0", url = "dubbo://127.0.0.1:12345")
//	@DubboComponentScan
//	@DubboService
//	private DemoService demoService;

    //不建议在Configuration配置DubboReference，如果有多个Service不同配置注入，则需要在每个引用的地方单独声明注解
	//注意Bean加载顺序不一致

	@DubboReference
	private RemoteFundInService simpleRemitInterface;

}