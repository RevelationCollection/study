package com.study.rpc.test.provider;

import com.study.rpc.test.HelloInterfacade;
import com.study.rpc.test.People;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HelloInterfaceadImpl implements HelloInterfacade {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public String sayHello(String name) {
        log.info("param:{}",name);
        return null;
    }

    @Override
    public List<People> findList(People people, Integer size) {
        log.info("param :{} ,{}",people,size);
        People tmp = new People();
        tmp.setName("reponse");
        tmp.setAge(2);
        tmp.setTime(new Date());
        ArrayList<People> list = new ArrayList<>();
        list.add(people);
        return list;
    }
}
