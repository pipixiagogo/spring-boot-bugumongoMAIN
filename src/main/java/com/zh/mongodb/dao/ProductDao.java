package com.zh.mongodb.dao;

import com.bugull.mongo.BuguDao;
import com.zh.mongodb.entity.Product;

//@Repository
public class ProductDao extends BuguDao<Product> {

    public ProductDao() {
        super(Product.class);
    }
}
