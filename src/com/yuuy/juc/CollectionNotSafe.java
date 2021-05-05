package com.yuuy.juc;

import java.util.ArrayList;
import java.util.List;

public class CollectionNotSafe {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        list.add("A");
        list.add("B");
        list.add("C");

        list.forEach(System.out::println);
    }
}
