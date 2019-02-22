package com.zh.mongodb.dao;

import com.bugull.mongo.BuguDao;
import com.zh.mongodb.entity.Good;

public class GoodDao extends BuguDao<Good> {
    public GoodDao(){
        super(Good.class);
    }
}
