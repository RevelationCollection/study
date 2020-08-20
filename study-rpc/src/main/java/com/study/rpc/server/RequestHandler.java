package com.study.rpc.server;

import com.study.rpc.common.protocol.MessageProtocol;
import com.study.rpc.common.protocol.Request;
import com.study.rpc.common.protocol.Response;
import com.study.rpc.common.protocol.Status;
import com.study.rpc.registry.register.ServiceObject;
import com.study.rpc.registry.register.ServiceRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 服务端，请求参数处理
 */
public class RequestHandler {

    private Logger log = LoggerFactory.getLogger(getClass());

    private MessageProtocol protocol;

    private ServiceRegister serviceRegistry;

    public RequestHandler(MessageProtocol protocol, ServiceRegister serviceRegistry) {
        super();
        this.protocol = protocol;
        this.serviceRegistry = serviceRegistry;
    }

    public byte[] handleRequest(byte[] data) throws Exception{
        //1、解组请求参数
        Request request = this.protocol.unmarshallingRequest(data);
        //2、查找本地服务对象
        ServiceObject serviceObject = this.serviceRegistry.getServiceObject(request.getServiceName());
        Response response = null;
        try {
            if(serviceObject==null){
                response = new Response(Status.NOT_FOUND);
            }else{
                //3、反射调用过程方法
                Class<?> interf = serviceObject.getInterf();
                Method method = interf.getMethod(request.getMethod(), request.getParamTypes());
                Object invoke = method.invoke(serviceObject.getObj(), request.getParams());
                response = new Response(Status.SUCCESS);
                response.setReturnValue(invoke);
            }
        } catch (Exception e) {
            log.error("error",e);
            response = new Response(Status.ERROR);
            response.setException(e);
        }
        //4、编组响应结果
        return this.protocol.marshallingResponse(response);
    }

    private <T>T getObject(Object obj,Class<T> cls){
        return (T) obj;
    }

    public MessageProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(MessageProtocol protocol) {
        this.protocol = protocol;
    }

    public ServiceRegister getServiceRegistry() {
        return serviceRegistry;
    }

    public void setServiceRegistry(ServiceRegister serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }
}
