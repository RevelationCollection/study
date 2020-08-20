package com.study.rpc.registry.discovery;

import com.study.rpc.registry.IRegistryServer;

import java.util.List;

public interface ServiceInfoDiscoverer extends IRegistryServer {

    /** 根据服务名称获取具体的服务信息 */
    List<ServiceInfo> getServiceInfo(String interfaceName);
}
