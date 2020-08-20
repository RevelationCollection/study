package com.study.rpc.registry.register;

import com.study.rpc.registry.IRegistryServer;

public interface ServiceRegister extends IRegistryServer {

    /**
     * 注册对象到注册中心中
     * @param serviceObject 真实业务对象
     * @param portocol rpc协议
     * @param port 端口
     * @throws Exception 异常
     */
    void register(ServiceObject serviceObject,String portocol,int port) throws  Exception;

    /**
     * 获取业务对象
     * @param name 接口名称
     * @return 业务对象
     * @throws Exception 异常
     */
    ServiceObject getServiceObject(String name) throws Exception;
}
