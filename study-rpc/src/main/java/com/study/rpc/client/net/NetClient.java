package com.study.rpc.client.net;

import com.study.rpc.registry.discovery.ServiceInfo;

public interface NetClient {

    /**
     * 发生网络请求，获取结果
     * @param data 请求参数
     * @param serviceInfo 请求的服务器信息
     * @return 返回结果
     */
    byte[] sendRequest(byte[] data, ServiceInfo serviceInfo) throws Throwable;
}
