package com.yuuy.designpattern.creation.factory.factorymethod;

public class FactoryB implements IFactory {
    @Override
    public IProduct create() {
        return (IProduct) new ProductB();
    }
}
