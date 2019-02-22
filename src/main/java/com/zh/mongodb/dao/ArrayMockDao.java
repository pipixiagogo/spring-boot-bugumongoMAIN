package com.zh.mongodb.dao;

import com.bugull.mongo.BuguDao;
import com.zh.mongodb.entity.ArrayMock;

//@Repository
public class ArrayMockDao extends BuguDao<ArrayMock> {
    public ArrayMockDao(){
        super(ArrayMock.class);
    }
}
