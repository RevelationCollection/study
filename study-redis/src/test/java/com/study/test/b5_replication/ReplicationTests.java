package com.study.test.b5_replication;

import com.study.redis.b5_replication.ReplicationExampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("replication")
@ContextConfiguration("classpath:applicationContext.xml")
public class ReplicationTests {

    @Resource
    private ReplicationExampleService  replicationExampleService;

    @Test
    public void setTest(){
        replicationExampleService.setCache("test_key","123");
    }
}
