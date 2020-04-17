package lock;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * 抽象队列同步器
 * acquire、acquireShare:定义了资源争用逻辑，如果没拿到，则等待
 * tryAcquire、tryAcauireShare:实际执行占用资源的操作，如果判定由使用者去实现
 * release、releaseShare:定义了释放资源的逻辑，释放之后，通知后续节点进行争抢
 * tryRelease、tryReleaseShare:实际执行资源释放的操作，具体的aqs使用者去实现
 */
public class WyAqs {
    //当前获取到锁的线程
    volatile AtomicReference<Thread> owner = new AtomicReference<>();

    //正在等待的线程
    volatile BlockingQueue<Thread> waiters = new LinkedBlockingDeque<>();

    //资源状态
    volatile AtomicInteger state = new AtomicInteger();

    public boolean tryAcquire(){
        throw new UnsupportedOperationException();
    }


    public void acquire(){
        boolean addFlag = true;
        while (!tryAcquire()){
            if(addFlag){
                //没拿到锁，加入等待集合
                waiters.offer(Thread.currentThread());
                addFlag = false;
            }else{
                //阻塞
                LockSupport.park();
            }
        }
        waiters.remove(Thread.currentThread());
    }

    public void release(){
        if(tryRelease()){
            Iterator<Thread> iterator = waiters.iterator();
            //通知等待者
            if(iterator.hasNext()){
                Thread next = iterator.next();
                LockSupport.unpark(next);
            }
        }
    }

    public boolean tryRelease(){
        throw new UnsupportedOperationException();
    }

    public void acquireShare(){
        boolean addFlag = true;
        //没有资源，进入等待
        while(tryAcquireShare()<0){
            if(addFlag){
                //没拿到锁，加入等待集合
                waiters.offer(Thread.currentThread());
                addFlag = false;
            }else{
                //阻塞
                LockSupport.park();
            }
        }
        waiters.remove(Thread.currentThread());
    }

    public int tryAcquireShare(){
        throw new UnsupportedOperationException();
    }

    public void releaseShare(){
        if(tryReleaseShare()){
//            Iterator<Thread> iterator = waiters.iterator();
//            //通知等待者
//            if(iterator.hasNext()){
//                Thread next = iterator.next();
//                LockSupport.unpark(next);
//            }
            Thread poll = waiters.peek();
            if(poll!=null){
                LockSupport.unpark(poll);
            }
        }
    }

    public boolean tryReleaseShare(){
        throw new UnsupportedOperationException();
    }

    public AtomicInteger getState(){
        return state;
    }
}
