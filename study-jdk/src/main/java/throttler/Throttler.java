package throttler;

/**
 * 节流、限流
 */
public interface Throttler {

    /**
     * 尝试申请一个配额
     * @param key 申请配额的key
     * @return 申请成功返回true 申请失败返回false
     */
    boolean tryAcquire(String key);
}
