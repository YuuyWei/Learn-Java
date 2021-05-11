package com.yuuy.designpattern.creation.factory.simplefactory;

import java.lang.reflect.InvocationTargetException;

public class SimpleFactory {

    IProduct create(Class<?> clazz) {
        try {
            return (IProduct) clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
