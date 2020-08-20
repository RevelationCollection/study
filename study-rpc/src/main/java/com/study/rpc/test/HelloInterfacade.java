package com.study.rpc.test;

import java.util.List;

public interface HelloInterfacade {

    String sayHello(String name);


    List<People> findList(People people,Integer size);
}
