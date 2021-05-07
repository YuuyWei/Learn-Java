package com.yuuy.designpattern.singleton;

/**
 * 懒汉式
 * 延迟加载的单例模式
 * 对象在第一次获取时加载
 * 多线程下容易出问题
 */
public class LazySingleton {
    private static LazySingleton lazySingleton;
    private int i;

    private LazySingleton() {
        System.out.println("----construct");
        i = 0;
    }

    public static LazySingleton getInstance() {
        System.out.println("----get instance");
        if (lazySingleton == null) {
            lazySingleton = new LazySingleton();
        }

        System.out.println("----return instance");
        return lazySingleton;
    }

    public int increaseFromZero() {
        System.out.println(i);
        return i++;
    }
}
