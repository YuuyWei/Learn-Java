package com.yuuy.designpattern.creation.factory.simplefactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 静态工厂
 * 侧重于对象的创建，对属性，初始化无能为力
 * 优点
 * 1. 单一职责：只负责实例化
 * 2. 依赖倒置：用户只需调用Vehicle接口，减少耦合
 * 缺点
 * - 违反开闭原则：新增一个类时需要对工厂类进行修改
 * 可以利用Java的反射机制进行改进，但是它的缺点
 * - 影响效率
 * - 需要运行时权限，某些特定环境不满足
 */
class SimpleFactoryTest {

    @Test
    void test() {
        assertEquals(new SimpleFactory().create(ProductA.class).saySomething(), "A");
        assertEquals(new SimpleFactory().create(ProductB.class).saySomething(), "B");
    }
}