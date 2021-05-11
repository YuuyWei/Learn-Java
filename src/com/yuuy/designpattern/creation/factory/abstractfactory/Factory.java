package com.yuuy.designpattern.creation.factory.abstractfactory;

public class Factory implements IFactory {
    @Override
    public IProductA createA() {
        return (IProductA) new ProductA();
    }

    @Override
    public IProductB createB() {
        return (IProductB) new ProductB();
    }
}
