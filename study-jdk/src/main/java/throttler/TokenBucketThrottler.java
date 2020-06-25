package throttler;

/**
 * 令牌桶
 * 从桶中取出令牌
 */
public class TokenBucketThrottler implements Throttler {

    /**
     * 当前剩余量
     */
    private long nowCount ;

    /**
     * 最后一次请求时间
     */
    private long lastRequestTime;

    /**
     * 桶的容量
     */
    private long capacity;

    /**
     * 桶漏完的总时间
     */
    private long totalTime;

    /**
     * 桶漏水的速度
     */
    private double velocity = capacity/totalTime;

    @Override
    public boolean tryAcquire(String key) {
        long now = System.currentTimeMillis();
        nowCount = Math.min(capacity,nowCount+ (long)((now-lastRequestTime)*velocity));
        if(nowCount-1>0){
            nowCount--;
            lastRequestTime = now;
            return true;
        }
        return false;
    }
}
