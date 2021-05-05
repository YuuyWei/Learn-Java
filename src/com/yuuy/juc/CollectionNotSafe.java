package com.yuuy.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

/**
 * 1. ArrayList is thread unsafe.
 *  - not in sequence when adding
 *  - ConcurrentModifiedException
 *      - Solution: 1. Vector : 读性能受限
 */
public class CollectionNotSafe {

    public static void main(String[] args) {
        List<String> list = new Vector<>();

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }

    }
}
