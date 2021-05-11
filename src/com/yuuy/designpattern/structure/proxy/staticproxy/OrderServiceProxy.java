package com.yuuy.designpattern.structure.proxy.staticproxy;

import com.yuuy.designpattern.structure.proxy.IOrderService;
import com.yuuy.designpattern.structure.proxy.Order;

/**
 * 只负责 orderService 数据源的切换
 * 不能切换其他 Service 数据源，局限性很大
 */
public class OrderServiceProxy implements IOrderService {

    private IOrderService orderService;

    public OrderServiceProxy(IOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public int createOrder(Order order) {
        before();
        int res = orderService.createOrder(order);
        after();
        return res;
    }

    private void before() {
        System.out.println("切换数据源");
    }

    private void after() {

    }
}
