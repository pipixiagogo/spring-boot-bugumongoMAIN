package com.zh.mongodb.entity;

import com.bugull.mongo.annotations.Entity;

@Entity
public class Device {
    private boolean online;

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
