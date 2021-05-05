package com.yuuy.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 资源类
class Ticket {

    private int rest = 100;
    private Lock lock = new ReentrantLock();

    public void sell() {
        lock.lock();
        try{
            if (rest > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出了第" +
                        rest-- + "张票，剩余" + rest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

/*
多线程开发套路
高内聚，低耦合：线程    资源类 操作
 */
public class SellTickets {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sell();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sell();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sell();
            }
        }, "C").start();
    }
}
