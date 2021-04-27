package com.study.dubbo.loadblance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class RoundRobinLoadBalance implements LoadBalance {

    private Logger log = LoggerFactory.getLogger(getClass());

    private static final int RECYCLE_PERIOD = 60000;

    private ConcurrentMap<String, ConcurrentMap<String, WeightedRoundRobin>> methodWeightMap = new ConcurrentHashMap<>();

    @Override
    public SmsChannel select(String location, List<SmsChannel> smsChannelList) {
        //from org.apache.dubbo.rpc.cluster.loadbalance.RoundRobinLoadBalance
        if (CollectionUtils.isEmpty(smsChannelList))
            return null;
        if (StringUtils.isEmpty(location)){
            location = "CN";
            log.info("location is empty :{},default route china",location);
        }
        String key = location;
        ConcurrentMap<String, WeightedRoundRobin> map = methodWeightMap.computeIfAbsent(key, k -> new ConcurrentHashMap<>());
        int totalWeight = 0;
        long maxCurrent = Long.MIN_VALUE;
        long now = System.currentTimeMillis();
        SmsChannel selectedInvoker = null;
        WeightedRoundRobin selectedWRR = null;
        for (SmsChannel smsChannel : smsChannelList) {
            int weight = getWeight(smsChannel);
            String channelCode = smsChannel.getChannelCode();
            WeightedRoundRobin weightedRoundRobin = map.computeIfAbsent(channelCode, k -> {
                WeightedRoundRobin wrr = new WeightedRoundRobin();
                wrr.setWeight(weight);
                return wrr;
            });

            if (weight != weightedRoundRobin.getWeight()) {
                //weight changed
                weightedRoundRobin.setWeight(weight);
            }
            long cur = weightedRoundRobin.increaseCurrent();
            weightedRoundRobin.setLastUpdate(now);
            if (cur > maxCurrent) {
                maxCurrent = cur;
                selectedInvoker = smsChannel;
                selectedWRR = weightedRoundRobin;
            }
            totalWeight += weight;
        }
        if (smsChannelList.size() != map.size()) {
            map.entrySet().removeIf(item -> now - item.getValue().getLastUpdate() > RECYCLE_PERIOD);
        }
        if (selectedInvoker != null) {
            selectedWRR.sel(totalWeight);
            return selectedInvoker;
        }
        // should not happen here
        return smsChannelList.get(0);
    }

    private int getWeight(SmsChannel smsChannel){
        return smsChannel.getWeight();
    }

    protected static class WeightedRoundRobin {
        private int weight;
        private AtomicLong current = new AtomicLong(0);
        private long lastUpdate;

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
            current.set(0);
        }

        public long increaseCurrent() {
            return current.addAndGet(weight);
        }

        public void sel(int total) {
            current.addAndGet(-1 * total);
        }

        public long getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(long lastUpdate) {
            this.lastUpdate = lastUpdate;
        }
    }
}
