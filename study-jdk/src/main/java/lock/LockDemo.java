package lock;

import java.util.concurrent.locks.Lock;

public class LockDemo {
    int i = 0;

//    Lock lock = new WyLockOld();
    Lock lock = new WyLock();

    public void add(){
        lock.lock();
        try{
            i++;
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LockDemo lockDemo = new LockDemo();

        for (int i = 0; i < 3; i++) {
            new Thread(()->{
                for (int j = 0; j < 10; j++) {
                    lockDemo.add();
                }
            }).start();
        }
        Thread.sleep(2000L);
        System.out.println(lockDemo.i);
    }
}

