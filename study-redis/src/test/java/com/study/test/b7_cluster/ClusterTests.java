package com.study.test.b7_cluster;

import com.study.redis.b7_cluster.ClusterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("cluster")
public class ClusterTests {
    @Resource
    private ClusterService clusterService;

    @Test
    public void setTest(){
        clusterService.set("toaa","hasda");
        clusterService.set("121","aaa");
        clusterService.set("wff","22");
    }

    // 测试cluster集群故障时的反应
    @Test
    public void failoverTest() {
        while (true) {
            try {
                long i = System.currentTimeMillis();
                clusterService.set("tony", i + "");
                // delay 10ms
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
