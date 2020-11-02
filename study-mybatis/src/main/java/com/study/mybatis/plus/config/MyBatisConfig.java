package com.study.mybatis.plus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {

    @Bean
    public MetaObjectHandler myMetaObjectHandler(){
        //字段自动填充值
        return new MyMetaObjectHandler();
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        //分页插件
        return new PaginationInterceptor();
    }
}
