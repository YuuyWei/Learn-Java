package com.yuuy.juc;

import java.util.concurrent.TimeUnit;

class Screen {

    synchronized void showFlower() throws InterruptedException{
        TimeUnit.SECONDS.sleep(4);
        System.out.println("----showing flower");
    }

    synchronized void showPeople() throws InterruptedException{
        System.out.println("----showing people");
    }

    void welcome() throws InterruptedException{
        System.out.println("----hello");
    }

}

/**
 * 线程8锁，线程A先启动，线程B后启动。先显示花还是人?
 * 1. 标准访问：花
 * 2. 花暂停4秒：花
 * 3. 增加一个普通方法welcome: 先显示hello
 * 4. 两个Screen对象
 * 5. 两个静态同步方法，两个Screen对象
 * 6. 两个静态同步方法，一个Screen对象
 * 7. 一个普通同步方法，一个静态同步方法，两个Screen对象
 * 8. 一个普通同步方法，一个静态同步方法，一个Screen对象
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
//                screen.showPeople();
                screen.welcome();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();
    }

}
