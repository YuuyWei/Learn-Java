package com.yuuy.designpattern.creation.factory.factorymethod;

public class FactoryA implements IFactory {
    @Override
    public IProduct create() {
        return (IProduct) new ProductA();
    }
}
