package com.zh.mongodb.dao;

import com.bugull.mongo.BuguDao;
import com.zh.mongodb.entity.Customer;

//@Repository
public class CustomerDao extends BuguDao<Customer>{
    public CustomerDao(){
        super(Customer.class);
    }
}
