package com.yuuy.designpattern.creation.factory.factorymethod;

import com.yuuy.designpattern.creation.factory.simplefactory.ProductA;
import com.yuuy.designpattern.creation.factory.simplefactory.ProductB;
import com.yuuy.designpattern.creation.factory.simplefactory.SimpleFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 工厂方法
 * 继承了静态工厂的优点
 * 除此之外，还遵循了开闭原则，对扩展开放，对修改封闭
 * 缺点
 * 产品种类太多的话，很容易使类的数量膨胀
 * 和静态工厂相比就是空间换时间
 *
 */
class FactoryMethodTest {

    @Test
    void test() {
        assertEquals(new FactoryA().create().saySomething(), "A");
        assertEquals(new FactoryB().create().saySomething(), "B");
    }
}