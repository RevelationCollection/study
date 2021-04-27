package com.study.dubbo.loadblance;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class Test {

    public static void main(String[] args) {
        testBalance();
    }

    private static void testBalance(){
        Test test = new Test();
        for (int i = 0; i < 100; i++) {
            SmsChannel select = test.select("123");
            System.out.println(select);
        }
    }

    private static final int RECYCLE_PERIOD = 60000;

    private ConcurrentMap<String, ConcurrentMap<String, RoundRobinLoadBalance.WeightedRoundRobin>> methodWeightMap = new ConcurrentHashMap<>();

    private List<SmsChannel> getList(){
        List<SmsChannel> list = new ArrayList<>();
        SmsChannel smsChannel = new SmsChannel();
        smsChannel.setWeight(1);
        smsChannel.setId(1L);
        smsChannel.setChannelCode("YS");
        list.add(smsChannel);
        smsChannel = new SmsChannel();
        smsChannel.setWeight(2);
        smsChannel.setId(2L);
        smsChannel.setChannelCode("TS");
        list.add(smsChannel);
        return list;
    }

    public SmsChannel select(String location) {
        //from org.apache.dubbo.rpc.cluster.loadbalance.RoundRobinLoadBalance
        List<SmsChannel> smsChannelList = getList();
        if (CollectionUtils.isEmpty(smsChannelList))
            return null;
        String key = location;
        ConcurrentMap<String, RoundRobinLoadBalance.WeightedRoundRobin> map = methodWeightMap.computeIfAbsent(key, k -> new ConcurrentHashMap<>());
        int totalWeight = 0;
        long maxCurrent = Long.MIN_VALUE;
        long now = System.currentTimeMillis();
        SmsChannel selectedInvoker = null;
        RoundRobinLoadBalance.WeightedRoundRobin selectedWRR = null;
        for (SmsChannel smsChannel : smsChannelList) {
            int weight = getWeight(smsChannel);
            String channelCode = smsChannel.getChannelCode();
            RoundRobinLoadBalance.WeightedRoundRobin weightedRoundRobin = map.computeIfAbsent(channelCode, k -> {
                RoundRobinLoadBalance.WeightedRoundRobin wrr = new RoundRobinLoadBalance.WeightedRoundRobin();
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
