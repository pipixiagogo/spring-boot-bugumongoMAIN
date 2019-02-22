package com.zh.mongodb.dao;

import com.bugull.mongo.BuguDao;
import com.zh.mongodb.entity.GroupProduct;

//@Repository
public class GroupProductDao extends BuguDao<GroupProduct> {
    public GroupProductDao() {
        super(GroupProduct.class);
    }
}
