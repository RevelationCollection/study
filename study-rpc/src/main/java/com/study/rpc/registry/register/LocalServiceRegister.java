package com.study.rpc.registry.register;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地注册中心
 */
public class LocalServiceRegister implements ServiceRegister {

    private Map<String,ServiceObject> serviceObjectMap = new ConcurrentHashMap<>();

    @Override
    public void register(ServiceObject serviceObject, String portocol, int port) throws Exception {
        serviceObjectMap.put(serviceObject.getName(),serviceObject);
    }

    @Override
    public ServiceObject getServiceObject(String name) throws Exception {
        return serviceObjectMap.get(name);
    }
}
