package com.study.springboot.actuator.custom;

import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.stereotype.Component;

@Endpoint(id="myEndpoint")
@Component
public class MyEndpoint {
    private String name = "default";

    @ReadOperation
    public String getName(){
        return "{\"name\":"+this.name+"\"}";
    }

    @DeleteOperation
    public void remove(){
        this.name = "";
    }

    @WriteOperation
    public void setName(@Selector String name){
        this.name = name;
    }
}
