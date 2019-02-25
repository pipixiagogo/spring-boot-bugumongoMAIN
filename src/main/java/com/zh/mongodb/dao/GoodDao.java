package com.zh.mongodb.dao;

import com.zh.mongodb.common.BuguPageDao;
import com.zh.mongodb.entity.Good;

public class GoodDao extends BuguPageDao<Good> {
    public GoodDao(){
        super(Good.class);
    }
}
