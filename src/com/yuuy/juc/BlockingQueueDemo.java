package com.yuuy.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列
 * BlockingQueue <- Queue <- Collection
 * 当队列满时调用add或者当队列空时调用remove会抛出异常
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(3);

        /*
        当队列满时调用add或者当队列空时调用remove会抛出异常
         */
//        for (int i = 0; i < 4; i++) {
//            blockingQueue.add(i);
//        }
//        for (int i = 0; i < 4; i++) {
//            blockingQueue.remove();
//        }

        /*
        当队列满时调用offer返回false或者当队列空时调用poll返回null
         */
//        for (int i = 0; i < 4; i++) {
//            System.out.println(blockingQueue.offer(i));
//        }
//        for (int i = 0; i < 4; i++) {
//            System.out.println(blockingQueue.poll());
//        }

        /*
        队列满或空时阻塞队列
         */
//        for (int i = 0; i < 4; i++) {
//            blockingQueue.put(i);
//        }
//        for (int i = 0; i < 4; i++) {
//            System.out.println(blockingQueue.take());
//        }

        /*
        队列满或空时阻塞，直到一个制定的时间，并且拥有返回值
         */
        for (int i = 0; i < 4; i++) {
            blockingQueue.offer(i, 3L, TimeUnit.SECONDS);
        }
        for (int i = 0; i < 4; i++) {
            System.out.println(blockingQueue.poll(3L, TimeUnit.SECONDS));
        }
    }
}
