package com.study.spring.cloud.bus.config;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

public class MyBusEvent extends RemoteApplicationEvent {
    public MyBusEvent() {
    }

    public MyBusEvent(Object source, String originService) {
        super(source, originService, "**");
    }
}
