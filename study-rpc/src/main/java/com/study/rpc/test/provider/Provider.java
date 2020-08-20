package com.study.rpc.test.provider;

import com.study.rpc.common.protocol.JavaSerializeMessageProtocol;
import com.study.rpc.registry.register.ServiceObject;
import com.study.rpc.registry.register.ServiceRegister;
import com.study.rpc.registry.register.ZookeeperExportServiceRegister;
import com.study.rpc.server.NettyRpcServer;
import com.study.rpc.server.RequestHandler;
import com.study.rpc.server.RpcServer;
import com.study.rpc.test.HelloInterfacade;
import com.study.rpc.util.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Provider {

    private static Logger logger = LoggerFactory.getLogger(Provider.class);

    public static void main(String[] args) throws Exception {
        logger.info("start server");
        String portStr = PropertiesUtils.getProperties("rpc.port");
        int port = Integer.parseInt(portStr);
        String portocol = PropertiesUtils.getProperties("rpc.portocol");
        //初始化实际服务应用
        HelloInterfacade helloInterfacade = new HelloInterfaceadImpl();
        //封装实际业务方法
        ServiceObject serviceObject = new ServiceObject(HelloInterfacade.class.getName(), helloInterfacade.getClass(), helloInterfacade);
        //设置注册中心
        ServiceRegister serviceRegister = new ZookeeperExportServiceRegister();
        //注册服务
        serviceRegister.register(serviceObject,portocol,port);
        //设置处理业务的handler方法
        RequestHandler requestHandler = new RequestHandler(new JavaSerializeMessageProtocol(), serviceRegister);
        //设置服务，并启动
        RpcServer rpcServer = new NettyRpcServer(port,portocol,requestHandler);
        rpcServer.start();
        int read = System.in.read();
        logger.info("input:   --->  "+read);
        rpcServer.stop();
        logger.info("server stop");
    }
}
