package com.study.rpc.test.consumer;

import com.alibaba.fastjson.JSON;
import com.study.rpc.client.ClientStubProxyFactory;
import com.study.rpc.client.net.NettyNetClinet;
import com.study.rpc.common.protocol.JavaSerializeMessageProtocol;
import com.study.rpc.common.protocol.MessageProtocol;
import com.study.rpc.registry.discovery.ZookeeperServiceInfoDiscoverer;
import com.study.rpc.test.HelloInterfacade;
import com.study.rpc.test.People;
import com.study.rpc.util.PropertiesUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Consumer {

    public static void main(String[] args) {

        ClientStubProxyFactory clientStubProxyFactory = new ClientStubProxyFactory();
        //设置注册中心
        clientStubProxyFactory.setServiceInfoDiscoverer(new ZookeeperServiceInfoDiscoverer());
        Map<String, MessageProtocol> supportMessagePortocol = new HashMap<>();
        String portocol = PropertiesUtils.getProperties("rpc.portocol");
        supportMessagePortocol.put(portocol,new JavaSerializeMessageProtocol());
        //设置支持的协议
        clientStubProxyFactory.setSupportMeesagePortocols(supportMessagePortocol);
        //设置网络连接客户端
        clientStubProxyFactory.setNetClient(new NettyNetClinet());
        HelloInterfacade helloInterfacade = clientStubProxyFactory.getProxy(HelloInterfacade.class);
        String response = helloInterfacade.sayHello("Hello World!");
        System.out.println("repsonse:"+response);
        People people = new People();
        people.setName("test-param");
        people.setAge(1);
        people.setTime(new Date());
        List<People> list = helloInterfacade.findList(people, 5);
        System.out.println("list:"+ JSON.toJSONString(list));
    }
}
