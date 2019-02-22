package com.zh.mongodb.dao;


import com.bugull.mongo.BuguDao;
import com.zh.mongodb.entity.User;

//@Repository
public class UserDao extends BuguDao<User>{
    public UserDao() {
        super(User.class);
    }
}
