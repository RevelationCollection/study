package com.study.dubbo.spi.impl;

import com.study.dubbo.spi.Robot;

public class OptimusPrime implements Robot {
    
    @Override
    public void sayHello() {
        System.out.println("Hello, I am Optimus Prime.");
    }
}