package com.study.util.spi.impl;

import com.study.util.spi.MySpiService;

public class FirstSpiServiceImpl  implements MySpiService {


    @Override
    public void doHandle(String str) {
        System.out.println("first spi println");
    }
}
