package com.study.rpc.registry.register;

public class ServiceObject {

    private String name;

    private Class<?> interf;

    private Object obj;

    public ServiceObject(String name, Class<?> interf, Object obj) {
        this.name = name;
        this.interf = interf;
        this.obj = obj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getInterf() {
        return interf;
    }

    public void setInterf(Class<?> interf) {
        this.interf = interf;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
