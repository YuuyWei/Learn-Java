package com.yuuy.designpattern.creation.builder;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 建造者模式和工厂方法模式十分相似 但是两者的侧重点有所不同
 * 建造着模式侧重于对复杂对象的初始化
 * 工厂模式侧重于对象实例的创建
 */
class BuilderTest {

    @Test
    void testBuilder() {
        Product.Builder builder = new Product.Builder();
        builder.addId(1)
                .addName("Yuuy")
                .addFullName("Yuuy Wei")
                .addAddress("China")
                .addPhoneNumber("12345678901");
        Product product = builder.build();
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info(() -> product.toString());
    }

}