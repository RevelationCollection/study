package com.study.redis.a2_geo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Profile("geo")
public class GeoExampleService {

    @Autowired
    private RedisTemplate redisTemplate;

    //上传位置
    public void add(Point point, String userId){
        RedisGeoCommands.GeoLocation<String> location = new RedisGeoCommands.GeoLocation<>(userId,point);
        redisTemplate.opsForGeo().add("user_geo",location);

    }

    /**
     * 附近的人
     * @param point 用户自己的位置
     * @return
     */
    public GeoResults<RedisGeoCommands.GeoLocation> near(Point point){
        //半径100米
        Distance distance = new Distance(100, RedisGeoCommands.DistanceUnit.METERS);
        Circle circle = new Circle(point, distance);
        //附件五个人
        RedisGeoCommands.GeoRadiusCommandArgs geoRadiusCommandArgs = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeCoordinates().limit(5);
        GeoResults<RedisGeoCommands.GeoLocation> results = redisTemplate.opsForGeo().radius("user_geo", circle,geoRadiusCommandArgs);
        return results;
    }
}
