package com.zh.mongodb.dao;

import com.bugull.mongo.BuguDao;
import com.zh.mongodb.entity.ListMock;

//@Repository
public class ListMockDao extends BuguDao<ListMock> {
    public ListMockDao() {
        super(ListMock.class);
    }
}
