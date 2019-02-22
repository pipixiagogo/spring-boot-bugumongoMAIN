package com.zh.mongodb.common;

import com.bugull.mongo.SimpleEntity;

import java.util.Date;

public abstract class UpgradeData extends SimpleEntity {

    public abstract String getDeviceName();

    public abstract String getType() ;

    public abstract String getBatchCode();

    public abstract String getVersion();

    public abstract int getStatus() ;

    public abstract boolean isReupgrade() ;

    public abstract Date getReqTime();

    public abstract Date getDownloadStartTime() ;

    public abstract Date getDownloadEndTime() ;

    public abstract Date getUpgradeStartTime() ;

    public abstract Date getUpgradeEndTime() ;

    public abstract Date getTransStartTime();

    public abstract Date getTransEndTime() ;

    public abstract int getDownloadCode();

    public abstract byte getUpgradeCode() ;

    public abstract int getTransCode() ;

    public abstract String getRdbsn();

}
