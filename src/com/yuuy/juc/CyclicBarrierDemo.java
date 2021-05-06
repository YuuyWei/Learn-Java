package com.yuuy.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier 和 CountDownLatch 有些相似之处
 * 但两者的侧重点不同：
 * CyclicBarrier 强调的是多个线程之间的状态同步，都到达同一个状态。然后再执行下一个操作。
 * CountDownLatch 强调的是某个线程必须等待其他线程完成任务，才能执行下一步操作。
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        int N = 7;
        CyclicBarrier cyclicBarrier= new CyclicBarrier(N, () -> {
            System.out.println("开始开会");
        });

        for (int i = 0; i < N; i++) {
            final int finalI = i;
            new Thread(() -> {
                System.out.println("第" + finalI + "人已到场");
                try {
                    cyclicBarrier.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }

    }
}
