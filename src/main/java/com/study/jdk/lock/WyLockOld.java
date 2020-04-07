package com.study.jdk.lock;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class WyLockOld implements Lock {

    //当前获取到锁的线程
    private volatile AtomicReference<Thread> lockThread = new AtomicReference<Thread>();

    //正在等待的线程
    private volatile BlockingQueue<Thread> waiters = new ArrayBlockingQueue<Thread>(100);


    public void lock() {
        boolean addFlag = true;
        while (!tryLock()){
            if(addFlag){
                //没拿到锁，加入等待集合
                waiters.offer(Thread.currentThread());
                addFlag = false;
            }else{
                System.out.println(Thread.currentThread().getName()+",获取锁失败，阻塞");
                //阻塞
                LockSupport.park();
            }
        }
        System.out.println(Thread.currentThread().getName()+",加锁成功");
        waiters.remove(Thread.currentThread());
    }

    public void lockInterruptibly() throws InterruptedException {

    }

    public boolean tryLock() {
        return lockThread.compareAndSet(null,Thread.currentThread());
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public void unlock() {
        if(lockThread.compareAndSet(Thread.currentThread(),null)){
            System.out.println(Thread.currentThread().getName()+",释放锁");
            Thread poll = waiters.peek();
            if(poll!=null){
                System.out.println(poll.getName()+",尝试获取锁");
                LockSupport.unpark(poll);
            }
        }
    }

    public Condition newCondition() {

        return null;
    }
}
