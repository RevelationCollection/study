package com.study.spring.cloud.bus.event;

import org.springframework.context.ApplicationEvent;

public class MyApplicationEvent extends ApplicationEvent {
    /**
     * 自定义事件
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public MyApplicationEvent(String  source) {
        super(source);
    }


}
