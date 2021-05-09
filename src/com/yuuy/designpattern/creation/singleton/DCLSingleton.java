package com.yuuy.designpattern.creation.singleton;

/**
 * 双重校验锁
 * 只会锁创建过程，后续的获取过程不锁
 * 保证一定的效率
 */
public class DCLSingleton {
    private static DCLSingleton dCLSingleton;
    private int i;

    private DCLSingleton() {
        System.out.println("----construct");
        i = 0;
    }

    public static DCLSingleton getInstance() {
        System.out.println("----get instance");

        if (dCLSingleton == null) {
            synchronized (DCLSingleton.class) {
                if (dCLSingleton == null) {
                    dCLSingleton = new DCLSingleton();
                }
            }
        }
        System.out.println("----return instance");

        return dCLSingleton;
    }

    public int increaseFromZero() {
        System.out.println(i);
        return i++;
    }
}
