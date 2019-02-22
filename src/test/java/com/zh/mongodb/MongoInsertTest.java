package com.zh.mongodb;

import com.bugull.mongo.BuguQuery;
import com.zh.mongodb.dao.UserDao;
import com.zh.mongodb.entity.Address;
import com.zh.mongodb.entity.Contact;
import com.zh.mongodb.entity.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MongoInsertTest extends ReplicaSetBaseTest{

   // @Test
    public void insertTest(){
        connectDB();
        UserDao userDao=new UserDao();
        List<User> users=new ArrayList<>();
        List<Address>list=new ArrayList<>();
        Calendar rightNow = Calendar.getInstance();
        rightNow.set(2018, 1, 15, 23, 59, 59);
        for(int i=0;i<5000;i++){
           // if(i%2==1){
                Address address=new Address("福州","福建","晋安");
                float[] floats=new float[]{6.6F,8.8F};
                list.add(address);
                userDao.insert(new User("张三"+i,i-1,false,rightNow.getTime(),new Contact(i+"webChat.com","999"+i),list,floats));
//            }else {
//                Address address=new Address("武汉","湖北","江夏");
//                list.add(address);
//                float[] floats=new float[]{1.1F,2.2F};
//                users.add(new User("王二"+i,i,false,new Date(),new Contact(i+"qq.com","123"+i),list,floats));
//            }
        }
        userDao.insert(users);
        disconnectDB();
    }

    @Test
    public void testDrop(){
        connectDB();
        UserDao userDao = new UserDao();
        BuguQuery<User> query = userDao.query();
        User result = query.existsField("username").is("username", "张三1").result();
        System.out.println(result);
        BuguQuery username = query.regex("username", "2");
        double age = userDao.max("age", username);
        System.out.println(age);
        //User andRemove = userDao.findAndRemove(query.regex(""));
       // System.out.println(andRemove);
        long l = userDao.countFast();
        System.out.println(l);

        disconnectDB();
    }


}
