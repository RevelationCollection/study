package com.study.jdk.lock;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    public static void main(String[] args) {
        WySemaphore semaphore = new WySemaphore(5);
//        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 9; i++) {
            String vip = "VIP-"+i;
            new Thread(()->{
                try {
                    semaphore.acquire();
                    print(vip);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                semaphore.release();
            }).start();
        }
    }

    public static void print(String vip) throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+"，会员进入："+vip);
        Thread.sleep(2000L);
        System.out.println(Thread.currentThread().getName()+"，会员结束："+vip);
    }
}
