package com.yuuy.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
