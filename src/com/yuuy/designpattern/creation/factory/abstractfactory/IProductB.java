package com.yuuy.designpattern.creation.factory.abstractfactory;

public interface IProductB {

    default String saySomething() {
        return "B";
    }
}
