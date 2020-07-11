package com.study.redis.a0_example;

import com.study.redis.entity.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("single")
public class SpringCacheService {

    @Cacheable(cacheManager = "cacheManager" ,value = "cache-1",key="#userId")
    public User findUserById(String userId){
        User user = new User(userId,"name-"+userId);
        System.out.println("database value is:"+user);
        return user;
    }

    @CacheEvict(cacheManager = "cacheManager",value = "cache-1",key="#userId")
    public void removeUserById(String userId){
        System.out.println("database remove suc :"+userId);
    }

    @CachePut(cacheManager = "cacheManager",value = "cache-1",key="#user.userId", condition = "#result ne null")
    public User updateUser(User user){
        System.out.println("databse update is suc:"+user);
        return user;
    }

}
