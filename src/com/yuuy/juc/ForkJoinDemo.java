package com.yuuy.juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 问题：计算斐波那契数列
 * 要求使用ForkJoin框架进行多线程计算
 */
class MyTask extends RecursiveTask<Integer> {
    private int N;

    public MyTask(int n) {
        N = n;
    }

    @Override
    protected Integer compute() {
        if (N == 0) return 0;
        if (N == 1) return 1;
        MyTask task1 = new MyTask(N-1);
        task1.fork();
        MyTask task2 = new MyTask(N-2);
        int result = task1.join() + task2.compute();
        return result;
    }
}

public class ForkJoinDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        MyTask task = new MyTask( 30);

        try {
            pool.execute(task);
        } finally {
            pool.shutdown();
        }

        System.out.println(task.get());
    }
}
