package com.yuuy.designpattern.creator.factory;

interface Vehicle{}
class Bike implements Vehicle{}
class Car implements Vehicle{}
class Truck implements Vehicle{}

/**
 * 静态工厂符合的原则
 * 1. 单一职责：只负责实例化
 * 2. 依赖倒置：用户只需调用Vehicle接口，减少耦合
 * 但是静态工厂类打破了
 * - 开闭原则：新增一个类时需要对工厂类进行修改
 * 可以利用Java的反射机制进行改进，但是它的缺点
 * - 影响效率
 * - 需要运行时权限，某些特定环境不满足
 * 再叠加一个Vehicle接口里的newInstance()方法
 */
public class SimpleFactory {
    public enum VehicleType {
        Bike, Car, Truck;
    }

    public static Vehicle createVehicle(VehicleType type) {
        switch (type) {
            case Car:
                return new Car();
            case Bike:
                return new Bike();
            case Truck:
                return new Truck();
            default:
                return null;
        }
    }
}
