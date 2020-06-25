package throttler;

public class SimpleWindowThrottler implements Throttler {

    /**
     * 窗口时间
     */
    private long minWindowsTime;

    /**
     * 最后一次请求时间
     */
    private long lastRequestTime=System.currentTimeMillis();

    /**
     * 窗口时间
     */
    private long limit = 20;

    /**
     * 计数器
     */
    private int count = 0;

    @Override
    public boolean tryAcquire(String key) {
        if(minWindowsTime >(System.currentTimeMillis()-lastRequestTime)){
            count = 0;
        }
        if (count>limit){
            return false;
        }else {
            count++;
            return true;
        }
    }
}
