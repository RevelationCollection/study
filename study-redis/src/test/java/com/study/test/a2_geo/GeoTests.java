package com.study.test.a2_geo;

import com.study.redis.a2_geo.GeoExampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("geo")
@ContextConfiguration("classpath:applicationContext.xml")
public class GeoTests {
    @Resource
    private GeoExampleService geoExampleService;

    @Test
    public   void testPut(){
        geoExampleService.add(new Point(117.405285, 40.904989),"张三");
        geoExampleService.add(new Point(117.405235, 40.904989),"李四");
        geoExampleService.add(new Point(117.405285, 40.904969),"王五");

        // 查找附近的人
        GeoResults<RedisGeoCommands.GeoLocation> geoResults = geoExampleService.near(new Point(117.405285, 40.904969));
        for (GeoResult<RedisGeoCommands.GeoLocation> geoResult : geoResults) {
            RedisGeoCommands.GeoLocation content = geoResult.getContent();
            System.out.println(content.getName() + " :" + geoResult.getDistance().getValue());
        }
    }
}
