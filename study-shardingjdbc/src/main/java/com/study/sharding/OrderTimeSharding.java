package com.study.sharding;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class OrderTimeSharding implements PreciseShardingAlgorithm<Date> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    Date[] dateRanges = new Date[2];

    {
        Calendar cal = Calendar.getInstance();
        cal.set(2019, 1, 1, 0, 0, 0);
        dateRanges[0] = cal.getTime();
        cal.set(2020, 1, 1, 0, 0, 0);
        dateRanges[1] = cal.getTime();
    }

    @Override
    public String doSharding(Collection<String> dataSourceCollection, PreciseShardingValue<Date> preciseShardingValue) {
        Date value = preciseShardingValue.getValue();
        logger.debug("dataSourceCollection:{}",dataSourceCollection);
        int size = dataSourceCollection.size();
        int i = 0;
        for (i = 0; i < dateRanges.length; i++) {
            Date date = dateRanges[i];
            if(value.before(date)){
                break;
            }
        }
        if(i>=size){
            i = size-1;
        }
        int j = 0;
        for (String s : dataSourceCollection) {
            if(j++==i){
                return  s;
            }
        }
        return null;
    }
}
