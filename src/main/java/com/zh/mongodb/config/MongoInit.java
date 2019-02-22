package com.zh.mongodb.config;

import com.bugull.mongo.BuguConnection;
import com.bugull.mongo.BuguFramework;
import com.zh.mongodb.util.PropertyUtil;

public class MongoInit  {

    public static void connect(){
        String mongoHost = PropertyUtil.getProperty(PropertiesConfig.MONGO_HOST);
        int mongoPort = PropertyUtil.getInteger(PropertiesConfig.MONGO_PORT);
        String mongoDB = PropertyUtil.getProperty(PropertiesConfig.MONGO_DB);
//		String mongoUser = PropertyUtil.getProperty(PropertiesConfig.MONGO_USERNAME);
//		String mongoPwd = PropertyUtil.getProperty(PropertiesConfig.MONGO_PASSWORD);
        BuguConnection mongoConnection = BuguFramework.getInstance().createConnection();
        mongoConnection.connect(mongoHost, mongoPort, mongoDB);
        System.out.println("MongodbInit启动");
    }
}
