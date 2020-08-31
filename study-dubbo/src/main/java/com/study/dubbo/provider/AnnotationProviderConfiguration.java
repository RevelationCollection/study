package com.study.dubbo.provider;


import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;


@EnableDubbo(scanBasePackages="com.study.dubbo.provider")
@PropertySource("classpath:/properties/dubbo-provider.properties")
public class AnnotationProviderConfiguration {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                AnnotationProviderConfiguration.class);
        context.start();
        System.in.read(); // 按任意键退出
        context.close();
    }
}
