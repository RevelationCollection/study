package com.study.dubbo.mock;

public class BarServiceMock implements  BarService{

    @Override
    public String testMockFunction(String param) {
        return "容错数据";
    }
}
