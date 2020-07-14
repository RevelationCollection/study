package com.study.test.b5_replication;

import com.study.redis.b5_replication.ReplicationExampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("replication-rw")
@ContextConfiguration("classpath:applicationContext.xml")
public class RepliactionRwTests {
    @Resource
    private ReplicationExampleService  replicationExampleService;

    @Test
    public void test(){
        replicationExampleService.setCache("reprw","value");
        String reprw = replicationExampleService.getCache("reprw");
        System.out.println("cache value is:"+reprw);
    }
}
