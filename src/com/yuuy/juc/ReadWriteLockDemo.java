package com.yuuy.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 读写锁是由两个锁组合而来，使得资源的写入具有排他性而多线程读取性能不受影响：
 1. read lock 加在只读操作上时，只要没有写入线程，只读操作可以由多个线程同时进行。
 2. write lock 加在写入操作上，具有排他性。
 */
class MyCache {
    private volatile Map<String, Object> map = new HashMap<>();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void put(String string, Object object) throws InterruptedException {
        readWriteLock.writeLock().lock();
        try {
            System.out.println("====正在写入" + string);
            TimeUnit.SECONDS.sleep(3);
            map.put(string, object);
            System.out.println("====写入成功");
        } finally {
            readWriteLock.writeLock().unlock();
        }

    }

    public void get(String string) throws InterruptedException {
        readWriteLock.readLock().lock();
        try {
            System.out.println("====正在读取");
            TimeUnit.SECONDS.sleep(3);
            Object res = map.get(string);
            System.out.println("====读取成功" + res);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(() -> {
                try {
                    myCache.put(String.valueOf(temp), temp);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(() -> {
                try {
                    myCache.get(String.valueOf(temp));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
