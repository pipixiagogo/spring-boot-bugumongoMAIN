package com.zh.mongodb.dao;

import com.bugull.mongo.BuguDao;
import com.zh.mongodb.entity.Order;

//@Repository
public class OrderDao extends BuguDao<Order>{
    public OrderDao() {
        super(Order.class);
    }
}
