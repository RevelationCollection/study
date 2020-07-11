package spi.impl;

import spi.MySpiService;

public class SecondSpiServiceImpl implements MySpiService{
    @Override
    public void doHandle() {
        System.out.println("second spi println");
    }
}
