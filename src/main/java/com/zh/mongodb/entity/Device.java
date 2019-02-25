package com.zh.mongodb.entity;

import com.bugull.mongo.annotations.Entity;

import java.util.Date;

@Entity
public class Device {
    private boolean online;
    private Date registDate;
    private Date lastOnlineTime;

    public Date getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(Date lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public Date getRegistDate() {
        return registDate;
    }

    public void setRegistDate(Date registDate) {
        this.registDate = registDate;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
