package com.yuuy.designpattern.structure.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 几乎负责所有数据源的切换，只要实现了某些特定方法
 */
public class OrderServiceProxy implements InvocationHandler {
    private Object target;

    public Object getInstance(Object target) {
        this.target = target;

        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before(args[0]);
        Object object = method.invoke(target, args);
        return object;
    }

    private void before(Object target) {
        System.out.println("target = " + target);
    }
}
