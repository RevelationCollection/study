package com.study.redis.b5_replication;

import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Profile({"replication","replication-rw"})
public class ReplicationExampleService {

    @Resource
    private StringRedisTemplate template;

    public void setCache(String key,String value){
        template.opsForValue().set(key,value);
    }

    public String getCache(String key){
        return template.opsForValue().get(key);
    }
}
