package com.yuuy.designpattern.singleton;

/**
 * 枚举实现
 * 最优美的实现
 * Enum 不能被继承，其他和常规类区别不大
 * 反序列化创建对象使用Enum，能防止反序列化重新创建对象
 * 第一次调用INSTANCE会创建一个对象，并且创建过程是线程安全的
 */
enum EnumSingleton {
    INSTANCE;
    private int i = 0;

    public int increaseFromZero() {
        System.out.println(i);
        return i++;
    }
}