package throttler;

/**
 * 漏水桶
 * 桶漏水的速度恒定
 */
public class LeakyBucketThrottler implements Throttler {

    /**
     * 桶剩余的水量
     */
    private long nowCount;

    /**
     * 最后一次请求的时间
     */
    private long lastRequestTime = System.currentTimeMillis();

    /**
     * 桶的容量
     */
    private long capacity = 10;

    /**
     * 一桶水漏完需要花费的时间
     */
    private long totalTime = 100;

    /**
     * 漏水速度
     */
    private double velocity = capacity/totalTime;

    @Override
    public boolean tryAcquire(String key) {
        long now = System.currentTimeMillis();
        nowCount = Math.max(0,nowCount - (long)((now-lastRequestTime)*velocity));
        if(nowCount+1<capacity){
            nowCount++;
            lastRequestTime=now;
            return true;
        }
        return false;
    }
}
