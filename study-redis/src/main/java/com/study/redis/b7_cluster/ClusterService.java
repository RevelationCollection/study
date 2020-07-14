package com.study.redis.b7_cluster;

import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Profile("cluster")
public class ClusterService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key,String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }
}
