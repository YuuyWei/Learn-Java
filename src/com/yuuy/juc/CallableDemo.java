package com.yuuy.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

class MyThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "****Coming here");
        TimeUnit.SECONDS.sleep(4);
        return 1024;
    }
}

/**
 * 多线程中，第3种获得多线程的方式
 * 1. get方法一般请放在最后一行，会导致阻塞
 * 2. FutureTask会缓存结果不会计算两次
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask(new MyThread());
//        FutureTask futureTask = new FutureTask(() -> {
//            System.out.println(Thread.currentThread().getName() + "****Coming here");
//            TimeUnit.SECONDS.sleep(4);
//            return 1024;
//        });

        // 会缓存结果，不会计算两次
        new Thread(futureTask, "A").start();
        new Thread(futureTask, "B").start();

        // 在此会阻塞
        System.out.println(futureTask.get());
        System.out.println(Thread.currentThread().getName() + "****Computed");
    }
}
