package com.study.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.InetAddress;

@Service
public class HostNameService {

    Logger log = LoggerFactory.getLogger(this.getClass());


    public  String getHostName(){
        try{
            return InetAddress.getLocalHost().getHostName();
        }catch (Throwable e){
            log.error("",e);
        }
        return null;
    }
}
