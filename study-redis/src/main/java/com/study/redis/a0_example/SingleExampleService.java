package com.study.redis.a0_example;

import com.study.redis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Profile("single")
public class SingleExampleService {
    //参数、值都是字符串
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //值由默认序列化
    @Resource
    private RedisTemplate redisTemplate;

    public void setByCache(String key,String value){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(key,value);
    }

    public User findUser(String userId){
        User user = (User) redisTemplate.opsForValue().get(userId);
        if (user!=null){
            System.out.println("cache value:"+user);
            return user;
        }
        //缓存数据不存在查询数据库
        user = new User(userId,"name-"+userId);
        System.out.println("databse value:"+user);
        redisTemplate.opsForValue().set(userId,user);
        return user;
    }
}
