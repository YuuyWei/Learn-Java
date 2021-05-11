package com.yuuy.designpattern.structure.proxy;

import com.yuuy.designpattern.structure.proxy.staticproxy.OrderServiceProxy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProxyTest {

    @Test
    void testOrderService() {
        Order order = new Order();
        OrderDao orderDao = new OrderDao();
        OrderService orderService = new OrderService(orderDao);
        assertEquals(1, orderService.createOrder(order));
    }

    @Test
    void testDynamicProxy() {
        Order order = new Order();
        System.out.println("order = " + order);
        OrderDao orderDao = new OrderDao();
        IOrderService orderService = (IOrderService) new com.yuuy.designpattern.structure.proxy.dynamicproxy.OrderServiceProxy()
                .getInstance(new OrderService(orderDao));
        assertEquals(1, orderService.createOrder(order));
    }

    @Test
    void testStaticProxy() {
        Order order = new Order();
        OrderDao orderDao = new OrderDao();
        OrderServiceProxy orderServiceProxy = new OrderServiceProxy(new OrderService(orderDao));
        assertEquals(1, orderServiceProxy.createOrder(order));
    }

}