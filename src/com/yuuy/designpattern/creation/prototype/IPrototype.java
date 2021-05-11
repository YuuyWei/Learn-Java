package com.yuuy.designpattern.creation.prototype;

/**
 * 这个泛型能让接口得知实现它的类的类型
 * @param <T>
 */
public interface IPrototype<T> {
    T clone();
}
