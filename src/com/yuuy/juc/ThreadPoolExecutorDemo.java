package com.yuuy.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Java线程池
 *
 * 为什么要用线程池？
 * 避免重复创建所带来的开销。
 *
 * 如何创建线程池？
 * ThreadPoolExecutor 实现自 ExecutorService -> Executor
 *
 * 有一个工具类 Executors 里面有3种创建线程池的静态方法。
 *  ExecutorService threadPool = Executors.newFixedThreadPool(5); // 固定数量
 *  ExecutorService threadPool = Executors.newCachedThreadPool(); // 数量可调整
 *  ExecutorService threadPool = Executors.newSingleThreadExecutor(); // 单一线程池
 *
 * 线程池的7个创建参数
 *   int corePoolSize 核心池大小，是线程池的主力
 *   int maximumPoolSize 最大池大小，是线程池临时工和主力
 *   long keepAliveTime 缩小线程池大小的等待时间
 *   TimeUnit unit 单位
 *   BlockingQueue<Runnable> workQueue 是等待队列
 *   ThreadFactory threadFactory 创建线程的工厂，一般用默认的就行
 *   RejectedExecutionHandler handler 线程的
 */
public class ThreadPoolExecutorDemo {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5); // 固定数量
//        ExecutorService threadPool = Executors.newCachedThreadPool(); // 数量可调整
//        ExecutorService threadPool = Executors.newSingleThreadExecutor(); // 单一线程池

        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
