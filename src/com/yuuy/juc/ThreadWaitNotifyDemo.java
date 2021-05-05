package com.yuuy.juc;

/**
 * 三步走：
 * 1. 判断
 * 2. 操作
 * 3. 通知
 */
class SharingData {
    private int state = 0;

    public synchronized void increment() throws InterruptedException {
        // 判断，必须用while，防止交互中多线程虚假唤醒
        while (state != 0) {
            this.wait();
        }

        // 干活
        state++;
        System.out.println(Thread.currentThread().getName() + "\t" + state);

        // 通知
        this.notifyAll();
    }

    public synchronized void decrement() throws InterruptedException {
        // 判断，必须用while
        while (state == 0) {
            this.wait();
        }

        // 干活
        state--;
        System.out.println(Thread.currentThread().getName() + "\t" + state);

        // 通知
        this.notifyAll();
    }
}

/**
 * 问题： 实现两个线程，其中一个可以操作初始值为1的变量。
 * 实现一个线程对该变量加1，一个线程减1。
 * 实现交替
 */
public class ThreadWaitNotifyDemo {
    public static void main(String[] args) {
        SharingData sharingData = new SharingData();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    sharingData.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    sharingData.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    sharingData.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    sharingData.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();
    }
}
