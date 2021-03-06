package com.yuuy.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三步走：
 * 1. 判断
 * 2. 操作
 * 3. 通知
 */
class SharingData {
    private int state = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws InterruptedException {
        lock.lock();
        try {
            // 判断，必须用while
            while (state != 0) {
                condition.await();
            }

            // 干活
            state++;
            System.out.println(Thread.currentThread().getName() + "\t" + state);

            // 通知
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws InterruptedException {

        lock.lock();
        try {
            // 判断，必须用while
            while (state == 0) {
                condition.await();
            }

            // 干活
            state--;
            System.out.println(Thread.currentThread().getName() + "\t" + state);

            // 通知
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

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
