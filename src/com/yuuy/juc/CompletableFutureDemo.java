package com.yuuy.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 无返回值
        CompletableFuture completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "存储数据");
        });

        // 有返回值
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            return "hello";
        }).exceptionally( throwable -> {
            throwable.printStackTrace();
            return "Exception";
        });
        System.out.println(completableFuture.get());
        System.out.println(stringCompletableFuture.get());
    }
}
