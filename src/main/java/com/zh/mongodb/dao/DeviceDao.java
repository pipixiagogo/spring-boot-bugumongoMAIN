package com.zh.mongodb.dao;

import com.bugull.mongo.BuguDao;
import com.zh.mongodb.entity.Device;

public class DeviceDao extends BuguDao<Device> {
    public DeviceDao(){
        super(Device.class);
    }
}
