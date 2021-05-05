package com.yuuy.juc;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 1. HashSet is thread unsafe.
 *  - not in sequence when adding
 *  - ConcurrentModifiedException
 *      - Solution: 1. Collections.synchronizedSet: 读性能依旧受限
 *                  2. CopyOnWriteSet : 只对写加锁，读性能不受影响
 */
public class CollectionNotSafe {

    public static void main(String[] args) {
        Set<String> set = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }

    }
}
