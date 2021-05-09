package com.yuuy.designpattern.creation.singleton;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class SingletonTest {
   @Test
   void testSingleton(){
       ExecutorService threadPool = Executors.newFixedThreadPool(3);
       try {
           for (int i = 0; i < 5; i++) {
               threadPool.execute(() -> {
                   Singleton singleton = Singleton.getInstance();
               });
           }
       } finally {
           threadPool.shutdown();
       }
       Singleton singleton = Singleton.getInstance();
       assertEquals(singleton.increaseFromZero(), 0);
       assertEquals(singleton.increaseFromZero(), 1);
       Singleton singleton2 = Singleton.getInstance();
       assertEquals(singleton.increaseFromZero(), 2);
   }

   @Test
   void testLazySingleton() {
       ExecutorService threadPool = Executors.newFixedThreadPool(3);
       try {
           for (int i = 0; i < 5; i++) {
               threadPool.execute(() -> {
                   LazySingleton singleton = LazySingleton.getInstance();
               });
           }
       } finally {
           threadPool.shutdown();
       }
       LazySingleton singleton = LazySingleton.getInstance();
       assertEquals(singleton.increaseFromZero(), 0);
       assertEquals(singleton.increaseFromZero(), 1);
       LazySingleton singleton2 = LazySingleton.getInstance();
       assertEquals(singleton.increaseFromZero(), 2);
   }

    @Test
    void testSICSingleton() {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        try {
            for (int i = 0; i < 5; i++) {
                threadPool.execute(() -> {
                    SICSingleton singleton = SICSingleton.getInstance();
                });
            }
        } finally {
            threadPool.shutdown();
        }
        SICSingleton singleton = SICSingleton.getInstance();
        assertEquals(singleton.increaseFromZero(), 0);
        assertEquals(singleton.increaseFromZero(), 1);
        SICSingleton singleton2 = SICSingleton.getInstance();
        assertEquals(singleton.increaseFromZero(), 2);
    }

    @Test
    void testEnumSingleton() {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        try {
            for (int i = 0; i < 5; i++) {
                threadPool.execute(() -> {
                    EnumSingleton singleton = EnumSingleton.INSTANCE;
                });
            }
        } finally {
            threadPool.shutdown();
        }
        EnumSingleton singleton = EnumSingleton.INSTANCE;
        assertEquals(singleton.increaseFromZero(), 0);
        assertEquals(singleton.increaseFromZero(), 1);
        EnumSingleton singleton2 = EnumSingleton.INSTANCE;
        assertEquals(singleton.increaseFromZero(), 2);
    }

    @Test
    void testDCLSingleton() {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        try {
            for (int i = 0; i < 5; i++) {
                threadPool.execute(() -> {
                    DCLSingleton singleton = DCLSingleton.getInstance();
                });
            }
        } finally {
            threadPool.shutdown();
        }
        DCLSingleton singleton = DCLSingleton.getInstance();
        assertEquals(singleton.increaseFromZero(), 0);
        assertEquals(singleton.increaseFromZero(), 1);
        DCLSingleton singleton2 = DCLSingleton.getInstance();
        assertEquals(singleton.increaseFromZero(), 2);
    }
}