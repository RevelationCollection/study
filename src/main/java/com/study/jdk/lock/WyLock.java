package com.study.jdk.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class WyLock  implements Lock {
    WyAqs aqs = new WyAqs() {
        @Override
        public boolean tryAcquire() {
            return owner.compareAndSet(null,Thread.currentThread());
        }

        @Override
        public boolean tryRelease(){
            return owner.compareAndSet(Thread.currentThread(),null);
        }
    };

    @Override
    public void lock() {
        aqs.acquire();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return aqs.tryRelease();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        aqs.release();
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
