package com.study.rpc.registry.discovery;

import java.net.InetSocketAddress;

public class ServiceInfo {
    /** 服务名称 */
    private String name;

    /** rpc协议 */
    private String protocol;

    /** 服务调用地址 */
    private InetSocketAddress address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public void setAddress(InetSocketAddress address) {
        this.address = address;
    }
}
