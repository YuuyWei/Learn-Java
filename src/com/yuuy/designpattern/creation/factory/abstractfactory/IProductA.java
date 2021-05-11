package com.yuuy.designpattern.creation.factory.abstractfactory;

public interface IProductA {

    default String saySomething() {
        return "A";
    }
}
