package com.study.dubbo.loadblance;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomLoadBalance implements LoadBalance {

    @Override
    public SmsChannel select(String location, List<SmsChannel> smsChannelList) {
        if (CollectionUtils.isEmpty(smsChannelList))
            return null;
        //from org.apache.dubbo.rpc.cluster.loadbalance.RandomLoadBalance
        // Number of invokers
        int length = smsChannelList.size();
        // Every invoker has the same weight?
        boolean sameWeight = true;
        // the weight of every invokers
        int[] weights = new int[length];
        // the first invoker's weight
        int firstWeight = getWeight(smsChannelList.get(0));
        weights[0] = firstWeight;
        // The sum of weights
        int totalWeight = firstWeight;
        for (int i = 1; i < length; i++) {
            int weight = getWeight(smsChannelList.get(i));
            // save for later use
            weights[i] = weight;
            // Sum
            totalWeight += weight;
            if (sameWeight && weight != firstWeight) {
                sameWeight = false;
            }
        }
        if (totalWeight > 0 && !sameWeight) {
            // If (not every invoker has the same weight & at least one invoker's weight>0), select randomly based on totalWeight.
            int offset = ThreadLocalRandom.current().nextInt(totalWeight);
            // Return a invoker based on the random value.
            for (int i = 0; i < length; i++) {
                offset -= weights[i];
                if (offset < 0) {
                    return smsChannelList.get(i);
                }
            }
        }
        // If all invokers have the same weight value or totalWeight=0, return evenly.
        return smsChannelList.get(ThreadLocalRandom.current().nextInt(length));
    }

    private int getWeight(SmsChannel smsChannel){
        return smsChannel.getWeight();
    }
}
