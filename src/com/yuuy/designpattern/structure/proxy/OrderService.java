package com.yuuy.designpattern.structure.proxy;


public class OrderService implements IOrderService {
    private OrderDao orderDao;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public int createOrder(Order order) {
        return orderDao.insert(order);
    }
}
