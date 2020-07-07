package com.study.util.spi;

import sun.misc.Service;

import java.util.Iterator;
import java.util.ServiceLoader;

public class TestSpiMain {

    public static void main(String[] args) {
        Iterator<MySpiService> providers  = Service.providers(MySpiService.class);
        ServiceLoader<MySpiService> load = ServiceLoader.load(MySpiService.class);
        while (providers.hasNext()){
            MySpiService next = providers.next();
            next.doHandle("1");
        }
        System.out.println("-----------");
        Iterator<MySpiService> iterator = load.iterator();
        while (iterator.hasNext()){
            MySpiService next = iterator.next();
            next.doHandle("2");

        }
    }
}
