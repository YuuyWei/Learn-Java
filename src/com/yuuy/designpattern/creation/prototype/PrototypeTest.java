package com.yuuy.designpattern.creation.prototype;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 通过克隆创建新的对象
 * 直接从内存复制出新的对象，减少实例化和初始化带来的开销
 * 适用于那些对象创建复杂，或者对象中关联硬件资源，以及部分属性变动的对象创建
 *
 * 深克隆可以通过对象序列化来进行
 *
 * 如果逻辑变化需要修改clone方法里面的代码，违背了开闭原则
 */
class PrototypeTest {

    @Test
    void test() {
        ConcretePrototype concretePrototype = new ConcretePrototype("origin");
        System.out.println("concretePrototype = " + concretePrototype);
        ConcretePrototype concretePrototype1 = concretePrototype.clone();
        concretePrototype1.setDesc("clone");
        System.out.println("concretePrototype1 = " + concretePrototype1);
    }
}