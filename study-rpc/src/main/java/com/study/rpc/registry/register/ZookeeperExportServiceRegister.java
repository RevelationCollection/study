package com.study.rpc.registry.register;

import com.alibaba.fastjson.JSON;
import com.study.rpc.registry.discovery.ServiceInfo;
import com.study.rpc.util.MyZkSerializer;
import com.study.rpc.util.PropertiesUtils;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URLEncoder;

public class ZookeeperExportServiceRegister extends LocalServiceRegister implements ServiceRegister {

    private Logger log = LoggerFactory.getLogger(getClass());

    private ZkClient zkClient;

    public ZookeeperExportServiceRegister() {
        String addr = PropertiesUtils.getProperties("zk.address");
        zkClient = new ZkClient(addr);
        zkClient.setZkSerializer(new MyZkSerializer());
    }

    @Override
    public void register(ServiceObject serviceObject, String portocol, int port) throws Exception {
        super.register(serviceObject,portocol,port);
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        String address = hostAddress+":"+port;
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setAddress(new InetSocketAddress(hostAddress,port));
        serviceInfo.setName(serviceObject.getName());
        serviceInfo.setProtocol(portocol);
        this.exportService(serviceInfo);
    }

    private void exportService(ServiceInfo serviceInfo){
        String serviceName = serviceInfo.getName();
        String uri = null;
        try {
            uri = JSON.toJSONString(serviceInfo);
            uri = URLEncoder.encode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("error",e);
        }
        String servicePath = ROOT_PATH + "/" + serviceName+"/service";
        boolean exists = zkClient.exists(servicePath);
        log.info("url :{},exists:{}",servicePath,exists);
        if(!exists){
            zkClient.createPersistent(servicePath,true);
//            zkClient.createEphemeral(servicePath);
        }
        String uriPath = servicePath+"/"+uri;
        if (zkClient.exists(uriPath)){
            zkClient.delete(uriPath);
        }
        zkClient.createEphemeral(uriPath);
        log.info("export interfacade success:"+serviceInfo.getName());
    }

}
