package com.zh.mongodb.dao;

import com.bugull.mongo.BuguDao;
import com.zh.mongodb.entity.EnumMock;

//@Repository
public class EnumMockDao extends BuguDao<EnumMock> {
    public EnumMockDao() {
        super(EnumMock.class);
    }
}
