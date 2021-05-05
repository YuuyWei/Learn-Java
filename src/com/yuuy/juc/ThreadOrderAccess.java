package com.yuuy.juc;

import jdk.jshell.EvalException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Resource {

    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();

    // A:1  B:2 C:3
    private int state = 1;

    public void printA() {
        lock.lock();

        try {
            // 判断
            while(state != 1) {
                conditionA.await();
            }

            // 干活
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }

            // 通知
            state = 2;
            conditionB.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void printB() {
        lock.lock();

        try {
            // 判断
            while(state != 2) {
                conditionB.await();
            }

            // 干活
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }

            // 通知
            state = 3;
            conditionC.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void printC() {
        lock.lock();

        try {
            // 判断
            while(state != 3) {
                conditionC.await();
            }

            // 干活
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }

            // 通知
            state = 1;
            conditionA.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}

/**
 * 多线程之间按顺序启动：A->B->C
 * A打印5次，B打印10次，C打印15次。
 * 总共10轮
 */
public class ThreadOrderAccess {
    public static void main(String[] args) {
        // 创建资源类
        Resource resource = new Resource();

        // 创建线程
        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                resource.printA();
            }
        }, "A").start();

        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                resource.printB();
            }
        }, "B").start();

        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                resource.printC();
            }
        }, "C").start();

    }
}
