package com.yuuy.designpattern.structure.proxy;

import java.sql.Date;

/**
 * 订单类
 */
public class Order {
    private Long id;
    private Date createTime;
    private Object orderInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Object getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(Object orderInfo) {
        this.orderInfo = orderInfo;
    }
}
