package com.yuuy.designpattern.creation.singleton;

/**
 * 静态内部类
 * 延迟加载的单例模式
 * 对象在第一次获取时加载
 * 无锁，并且多线程下运行良好
 * 使用序列化和反序列化会破坏单例，可以通过增加readResolve方法解决，但是仍会创建对象。
 */
public class SICSingleton {

    public static class SingletonHolder {
        private static SICSingleton singleton = new SICSingleton();
    }

    private int i;
    private SICSingleton() {
        System.out.println("----construct");
        i = 0;
    }
    public int increaseFromZero() {
        System.out.println(i);
        return i++;
    }

    public static SICSingleton getInstance() {
        System.out.println("----get instance");

        return SingletonHolder.singleton;
    }

}
