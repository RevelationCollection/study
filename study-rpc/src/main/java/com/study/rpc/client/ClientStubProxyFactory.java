package com.study.rpc.client;

import com.alibaba.fastjson.JSON;
import com.study.rpc.client.net.NetClient;
import com.study.rpc.common.protocol.MessageProtocol;
import com.study.rpc.common.protocol.Request;
import com.study.rpc.common.protocol.Response;
import com.study.rpc.registry.discovery.ServiceInfo;
import com.study.rpc.registry.discovery.ServiceInfoDiscoverer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ClientStubProxyFactory {

    private ServiceInfoDiscoverer serviceInfoDiscoverer;

    private Map<String, MessageProtocol> supportMeesagePortocols;

    private NetClient netClient;

    private Map<Class<?>,Object> cahceObjects = new HashMap<>();

    public <T>T getProxy(Class<T> intef){
        Object proxy = this.cahceObjects.get(intef);
        if(proxy==null){
            proxy = Proxy.newProxyInstance(intef.getClassLoader(),new Class<?>[]{intef},
                    new ClientStubInvocationHadnler(intef));
            this.cahceObjects.put(intef,proxy);
        }
        return (T) proxy;
    }


    private class ClientStubInvocationHadnler implements InvocationHandler{

        private Logger log = LoggerFactory.getLogger(getClass());

        private Class<?> interf;

        private Random random = new Random();

        public ClientStubInvocationHadnler(Class<?> interf){
            super();
            this.interf = interf;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            if("toString".equals(methodName)){
                return proxy.getClass().toString();
            }
            if("hashCode".equals(methodName)){
                return 0;
            }
            String interfName = this.interf.getName();
            //1、从注册中心 获取服务提供方的信息
            List<ServiceInfo> serverList = serviceInfoDiscoverer.getServiceInfo(interfName);
            if(serverList==null || serverList.isEmpty()){
                throw new Exception("远程服务不存在或远程服务未注册");
            }
            //随机选择一个服务器提供这
            ServiceInfo serviceInfo = serverList.get(random.nextInt(serverList.size()));
            log.info("serviceInfo:{}", JSON.toJSON(serviceInfo));
            //2、构造request对象
            Request request = new Request();
            request.setServiceName(serviceInfo.getName());
            request.setMethod(methodName);
            request.setParams(args);
            request.setParamTypes(method.getParameterTypes());
            //3、协议层编组 获得改方法的协议
            MessageProtocol messageProtocol = supportMeesagePortocols.get(serviceInfo.getProtocol());
            //编组请求
            byte[] data = messageProtocol.marshallingRequest(request);
            //4、调用网络层发送请求
            byte[] resp = netClient.sendRequest(data, serviceInfo);
            //5、解组响应信息
            Response response = messageProtocol.unmarshallingResponse(resp);
            //6、返回结果处理
            if(response.getException()!=null){
                throw response.getException();

            }
            return response.getReturnValue();
        }
    }

    public ServiceInfoDiscoverer getServiceInfoDiscoverer() {
        return serviceInfoDiscoverer;
    }

    public void setServiceInfoDiscoverer(ServiceInfoDiscoverer serviceInfoDiscoverer) {
        this.serviceInfoDiscoverer = serviceInfoDiscoverer;
    }

    public NetClient getNetClient() {
        return netClient;
    }

    public void setNetClient(NetClient netClient) {
        this.netClient = netClient;
    }

    public Map<String, MessageProtocol> getSupportMeesagePortocols() {
        return supportMeesagePortocols;
    }

    public void setSupportMeesagePortocols(Map<String, MessageProtocol> supportMeesagePortocols) {
        this.supportMeesagePortocols = supportMeesagePortocols;
    }
}
