package com.zh.mongodb;

import com.bugull.mongo.BuguConnection;
import com.bugull.mongo.BuguFramework;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: zh
 * @Date: 2018/12/20 14:43
 * @Description:
 */
public class ReplicaSetBaseTest {

        protected void connectDB(){
            List<ServerAddress> serverList = new ArrayList<ServerAddress>();
            serverList.add(new ServerAddress("192.168.1.128", 27017));
    //        serverList.add(new ServerAddress("192.168.1.248", 27018));
    //        serverList.add(new ServerAddress("192.168.32.248", 27019));


            BuguConnection conn = BuguFramework.getInstance().createConnection();
            conn.setServerList(serverList).setDatabase("MyMongoDB").connect();
    //                .setUsername("test")
    //                .setPassword("test")


        }

        protected void connectDBWithOptions(MongoClientOptions options){
            List<ServerAddress> serverList = new ArrayList<ServerAddress>();
            serverList.add(new ServerAddress("192.168.1.107", 27017));
    //        serverList.add(new ServerAddress("192.168.1.248", 27018));
    //        serverList.add(new ServerAddress("192.168.1.248", 27019));

            BuguConnection conn = BuguFramework.getInstance().createConnection();
            conn.setOptions(options);
            conn.setServerList(serverList);
            conn.setUsername("test");
            conn.setPassword("test");
            conn.setDatabase("test");
            conn.connect();
        }

        protected void disconnectDB(){
            BuguFramework.getInstance().destroy();
        }
}
