package com.study.spring.cloud.hystrix.rxjava;

import rx.Observable;
import rx.Single;
import rx.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;

public class RxJavaDemo {

    public static void main(String[] args) throws InterruptedException {
        //正常输出
        Single.just(new CustomAction().init())
                .subscribe(System.out::println);
        //自定义方法输出
        Single.just(new CustomAction().init())
                .subscribe(RxJavaDemo::println);
        //非主线程输出
        Single.just(new CustomAction().init())
                .subscribeOn(Schedulers.newThread())
                .subscribe(RxJavaDemo::println);
        //callback 写法
        Single.fromCallable(() -> "ok1")
                .subscribeOn(Schedulers.io())
                .subscribe(RxJavaDemo::isOK);
        //数据遍历
        printList();
        //扩展
        observerDemo();
        System.out.printf("[线程%s] 开始休眠\n", Thread.currentThread().getName());
        Thread.sleep(2000);
        System.out.printf("[线程%s] 休眠结束\n", Thread.currentThread().getName());
    }

    public static void observerDemo() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        Observable.from(list)
                .subscribe(next -> {
                            if (next > 2) {
                                throw new IllegalArgumentException("非法参数");
                            }
                            System.out.printf("[线程%s] 数据:%s \n", Thread.currentThread().getName(), next);
                        }, error ->
                            System.out.printf("[线程%s] 降级\n", Thread.currentThread().getName())
                        , () ->
                            System.out.printf("[线程%s] 执行结束\n", Thread.currentThread().getName())

                );
    }

    public static void printList() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        Observable.from(list)
                .subscribe(RxJavaDemo::println);

    }

    public static void println(Object value) {
        System.out.printf("[线程%s] 数据:%s \n", Thread.currentThread().getName(), value);
    }

    public static void isOK(Object val) {
        if ("ok".equals(val)) {
            System.out.printf("[线程%s] 执行成功\n", Thread.currentThread().getName());
            return;
        }
        System.out.printf("[线程%s] 不是ok\n", Thread.currentThread().getName());
    }

    public static class CustomAction {
        public String init() {
            return "⌚事件发布";
        }

    }
}
