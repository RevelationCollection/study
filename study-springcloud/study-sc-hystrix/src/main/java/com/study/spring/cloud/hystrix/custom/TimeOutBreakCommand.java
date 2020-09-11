package com.study.spring.cloud.hystrix.custom;

import java.util.Random;
import java.util.concurrent.*;

public class TimeOutBreakCommand implements Command<String> {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        TimeOutBreakCommand timeOutBreakHandle = new TimeOutBreakCommand();
        Future<String> future = executorService.submit(()->{
            return timeOutBreakHandle.run();
        });
        String response ;
        try {
            //配置超时等待
            response = future.get(100, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            //异常执行熔断方法
            response = timeOutBreakHandle.callback();
        }
        System.out.println("执行结果：" + response);
        executorService.shutdown();
    }

    private Random random = new Random();

    @Override
    public String run() {
        int sleep = random.nextInt(150);
        System.out.println("本次休眠：" + sleep);
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "正常响应";
    }

    @Override
    public String callback() {
        return "出错了，熔断响应";
    }
}
