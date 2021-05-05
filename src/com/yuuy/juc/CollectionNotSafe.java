package com.yuuy.juc;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 1. ArrayList is thread unsafe.
 *  - not in sequence when adding
 *  - ConcurrentModifiedException
 *      - Solution: 1. Vector  : 读性能受限
 *                  2. Collection.synchronizedList: 读性能依旧受限
 *                  3. CopyOnWriteArrayList : 只对写加锁，读性能不受影响
 */
public class CollectionNotSafe {

    public static void main(String[] args) {
        List<String> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }

    }
}
