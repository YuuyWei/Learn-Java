package com.yuuy.juc;

import java.util.concurrent.CountDownLatch;

/**
 * 关门游戏
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        int N = 6;
        CountDownLatch countDownLatch = new CountDownLatch(N);

        for (int i = 0; i < N; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "离开教室");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println("班长关门走人");
    }
}
