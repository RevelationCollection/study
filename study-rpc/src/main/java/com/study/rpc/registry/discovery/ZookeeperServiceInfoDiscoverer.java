package com.study.rpc.registry.discovery;

import com.alibaba.fastjson.JSON;
import com.study.rpc.util.MyZkSerializer;
import com.study.rpc.util.PropertiesUtils;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * zk服务发现
 */
public class ZookeeperServiceInfoDiscoverer implements ServiceInfoDiscoverer {

    private Logger log = LoggerFactory.getLogger(getClass());

    private ZkClient zkClient;

    public ZookeeperServiceInfoDiscoverer() {
        String addr = PropertiesUtils.getProperties("zk.address");
        zkClient = new ZkClient(addr);
        zkClient.setZkSerializer(new MyZkSerializer());
    }

    @Override
    public List<ServiceInfo> getServiceInfo(String interfaceName) {
        String serverPath = ROOT_PATH+"/"+interfaceName+"/service";
        log.info("url{} exists{}",serverPath,zkClient.exists(serverPath));
        List<String> children = zkClient.getChildren(serverPath);
        if(children==null || children.isEmpty()){
            return null;
        }
        List<ServiceInfo> list = new ArrayList<>(children.size());
        for (String child : children) {
            try {
                String decode = URLDecoder.decode(child, "UTF-8");
                ServiceInfo serviceInfo = JSON.parseObject(decode, ServiceInfo.class);
                list.add(serviceInfo);
            } catch (UnsupportedEncodingException e) {
                log.error("error",e);
            }
        }
        return list;
    }
}
