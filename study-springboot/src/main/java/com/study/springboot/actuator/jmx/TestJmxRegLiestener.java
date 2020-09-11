package com.study.springboot.actuator.jmx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestJmxRegLiestener implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private JmxTest jmxTest;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            //create Mbean
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            ObjectName jmxName = new ObjectName("jmxBean:name=netTestJmx");
            //注册
            server.registerMBean(jmxTest,jmxName);
            //注册一个端口，绑定url后客户端可以使用rmi通过url方式访问
            Registry registry = LocateRegistry.createRegistry(1099);
            //构造jmxServiceUrl
            JMXServiceURL jmxServiceURL = new JMXServiceURL("service:jmx:rmi///jndi/rmi://localhost:1099/jmxrmi");
            JMXConnectorServer jmxConnectorServer = JMXConnectorServerFactory.newJMXConnectorServer(jmxServiceURL, null, server);
            jmxConnectorServer.start();
        }catch (Throwable e){
            logger.error("error",e);
        }
    }
}
