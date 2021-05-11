package com.yuuy.designpattern.creation.prototype;

public class ConcretePrototype implements IPrototype<ConcretePrototype> {
    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String desc;

    public ConcretePrototype(String desc) {
        this.desc = desc;
    }

    @Override
    public ConcretePrototype clone() {
        return new ConcretePrototype(this.desc);
    }

    @Override
    public String toString() {
        return "ConcretePrototype{" +
                "desc='" + desc + '\'' +
                '}';
    }
}
