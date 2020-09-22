package com.study.spring.cloud.bus.event;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class EventConfiguration {

    @EventListener
    public void onEvent(MyApplicationEvent event){
        System.out.println("监听到事件:"+event.getSource());
    }
}
