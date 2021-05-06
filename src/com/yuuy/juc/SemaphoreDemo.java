package com.yuuy.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 抢占车位
 * 15辆车，3个车位
 * 随机抢车
 * 实际用途：
 * 1. 多个共享资源的互斥使用
 * 2. 并发线程数的控制
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 15; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("第" + Thread.currentThread().getName() + "辆车抢到车位");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("第" + Thread.currentThread().getName() + "辆车开车走了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }

    }
}
