package com.zh.mongodb.dao;

import com.bugull.mongo.BuguDao;
import com.zh.mongodb.entity.Account;

//@Repository
public class AccountDao extends BuguDao<Account> {

    public AccountDao(){
        super(Account.class);
    }
}
