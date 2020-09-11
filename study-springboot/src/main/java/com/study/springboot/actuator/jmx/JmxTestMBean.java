package com.study.springboot.actuator.jmx;

public interface JmxTestMBean {
    String getName();
    void setName(String name);
    String printHello();
    String printHello(String whoName);
}