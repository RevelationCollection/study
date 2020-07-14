package com.study.test.a0_example;


import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:")
public class JedisTests {

    public void list(){
//        Jedis jedis = new Jedis("192.168.100.241", 6379);
////        // 插入数据1 --- 2 --- 3
////        jedis.rpush("queue_1", "1");
////        jedis.rpush("queue_1", "2", "3");
////
////        List<String> strings = jedis.lrange("queue_1", 0, -1);
////        for (String string : strings) {
////            System.out.println(string);
////        }
////
////        // 消费者线程简例
////        while (true) {
////            String item = jedis.lpop("queue_1");
////            if (item == null) break;
////            System.out.println(item);
////        }
////
////        jedis.close();
    }
}
