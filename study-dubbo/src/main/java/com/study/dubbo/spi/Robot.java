package com.study.dubbo.spi;

import com.alibaba.dubbo.common.extension.SPI;

//Dubbo SPI 需要添加该注解
@SPI
public interface Robot {
    /** 测试方法 */
    void sayHello();
}
