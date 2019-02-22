package com.zh.mongodb.dao;

import com.bugull.mongo.BuguDao;
import com.zh.mongodb.entity.GroupContact;

//@Repository
public class GroupContactDao extends BuguDao<GroupContact> {
    public GroupContactDao() {
        super(GroupContact.class);
    }
}
