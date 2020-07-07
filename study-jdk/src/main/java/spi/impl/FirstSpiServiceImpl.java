package spi.impl;

import spi.MySpiService;

public class FirstSpiServiceImpl implements MySpiService {

    @Override
    public void doHandle() {
        System.out.println("first impl println");
    }
}
