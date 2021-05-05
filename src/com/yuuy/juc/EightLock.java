package com.yuuy.juc;

import java.util.concurrent.TimeUnit;

class Screen {

    synchronized void showFlower() throws InterruptedException{
        System.out.println("----showing flower");
    }

    synchronized void showPeople() throws InterruptedException{
        System.out.println("----showing people");
    }

}

/**
 * 线程8锁，线程A先启动，线程B后启动。先显示花还是人?
 * 1. 标准访问：花
 * 2. 花暂停4秒：
 */
public class EightLock {

    public static void main(String[] args) throws InterruptedException {
        Screen screen = new Screen();

        new Thread(() -> {

            try {
                screen.showFlower();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        TimeUnit.SECONDS.sleep(2);

        new Thread(() -> {

            try {
                screen.showPeople();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();
    }

}
