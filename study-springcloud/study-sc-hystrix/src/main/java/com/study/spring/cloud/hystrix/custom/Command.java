package com.study.spring.cloud.hystrix.custom;

public interface Command<T> {
    /**
     * 正常执行
     *
     * @return 返回正常响应
     */
    T run();

    /**
     * 熔断方法
     *
     * @return 返回异常结果
     */
    T callback();
}
