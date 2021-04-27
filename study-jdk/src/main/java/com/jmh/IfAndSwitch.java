package com.jmh;


import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)// 测试完成时间
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2, time=1,timeUnit=TimeUnit.SECONDS)// 预热 2 轮，每次 1s
@Measurement(iterations = 5,time = 1 ,timeUnit=TimeUnit.SECONDS)// 测试 5 轮，每次 3s
@Fork(1)// fork 1 个线程
@State(Scope.Thread)// 每个测试线程一个实例
public class IfAndSwitch {

    static Integer CORE_NUM = 9;

    public static void main(String[] args) throws RunnerException {
        System.out.println("run start");
        Options options = new OptionsBuilder()
                .include(IfAndSwitch.class.getName()) // 导入要测试的类
                .output("/Users/wy/Downloads/jmh.log") //输出测试结果文件
                .build();
        new Runner(options).run();
        System.out.println("run end ");
    }

    @Benchmark
    public void switchTest(){
        int num1;
        switch (CORE_NUM){
            case 1:
                num1 = 1 ;
                break;
            case 2:
                num1 = 3;
                break;
            case 3:
                num1 = 3;
                break;
            case 5:
                num1 = 5;
                break;
            case 7:
                num1 = 7;
                break;
            case 9:
                num1 = 9;
                break;
            default:
                num1 = -1;
                break;
        }
    }

    @Benchmark
    public void ifTest(){
        int num1;
        if (CORE_NUM ==1){
            num1 = 1;
        }else if(CORE_NUM == 2){
            num1 = 2;
        }else if (CORE_NUM == 3){
            num1 = 3;
        }else if (CORE_NUM == 5){
            num1 = 5;
        }else if (CORE_NUM == 7){
            num1 = 7;
        }else if (CORE_NUM == 9){
            num1 = 9;
        }else {
            num1 = -1;
        }
    }

}
