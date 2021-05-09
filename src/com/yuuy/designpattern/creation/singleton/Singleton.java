package com.yuuy.designpattern.creation.singleton;

/**
 * 饿汉式
 * 最简单的单例模式
 * 其对象在类加载阶段就已经生成
 */
public class Singleton {

    private static Singleton singleton = new Singleton();
    private int i;

    private Singleton() {
        System.out.println("----construct");
        i = 0;
    }

    public static Singleton getInstance() {
        System.out.println("----get instance");
        return singleton;
    }

    public int increaseFromZero() {
        System.out.println(i);
        return i++;
    }
}
