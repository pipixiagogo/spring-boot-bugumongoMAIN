package com.zh.mongodb;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zh.mongodb.dao.GoodDao;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CountGoodTest extends ReplicaSetBaseTest{
    @Test
    public void doCount1(){
        connectDB();
        GoodDao goodDao = new GoodDao();
        Iterable cat_id = goodDao.aggregate().count("cat_id").results();
        Iterator iterator = cat_id.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        disconnectDB();
    }

    //根据cat_id 分组 并求出其数量
    @Test
    public void doCount2(){
        connectDB();
        GoodDao goodDao = new GoodDao();
        Iterable results = goodDao.aggregate().group("{_id:'$cat_id',count:{$sum:1}}").sort("count:-1").results();
        Iterator iterator = results.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        disconnectDB();
    }

    @Test
    public void doCount3(){
       // connectDB();
//        GoodDao goodDao = new GoodDao();
//        goodDao.aggregate().group("_id：'$cat_id'")

        Map<String,Object> map=new HashMap<>();
        map.put("RDBSN","123");
        map.put("TIME",1550734354*1000L);
        map.put("COUNT",3);
        Map<String,Object> dataMap=new HashMap<>();

        dataMap.put("CANID","0A04FF07");
        dataMap.put("CANDATA","0000800200000000");
        map.put("DATA",dataMap);
        String s = map.toString();
       // System.out.println(s);
        Object o1 = JSONObject.toJSON(map);
        String s1 = o1.toString();
        System.out.println(s1);
        Set<String> strings = map.keySet();
        for(String str:strings){
            Object o = map.get(str);
            System.out.println(str+""+o);
        }

        // disconnectDB();
    }
    //db.good.aggregate([ {$match:{shop_price:{$gt:30}}}, {$group:{_id:"$cat_id",count:{$sum:1}}} ])
    //DB聚合查询 shop_price大于30  且按 cat_id分组.match("shop_price:{$gt:30}").
    @Test
    public void doCount4(){
        connectDB();
        GoodDao goodDao = new GoodDao();
        //字符串写法
//        Iterable results = goodDao.aggregate().match("{shop_price:{$gt:30}}").group("{_id:'$cat_id',count:{$sum:1}}").results();
//        Iterator iterator = results.iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }
        BasicDBObject dbObject=new BasicDBObject();
        //DB聚合查询 shop_price大于30   "{_id:'$cat_id',count:{$sum:1}}"
        //DBObject对象写法
//        dbObject.put("shop_price",new BasicDBObject("$gt",30));
//        BasicDBObject basicDBObject=new BasicDBObject();
//        basicDBObject.put("_id","$cat_id");
//        basicDBObject.put("count",new BasicDBObject("$sum",1));
//        Iterable results = goodDao.aggregate().match(dbObject).group(basicDBObject).results();

        //查询语句写法
        dbObject.put("_id","$cat_id");
        dbObject.put("sum",new BasicDBObject("$sum",1));
        Iterable results = goodDao.aggregate().match(goodDao.query().greaterThan("shop_price", 30)).group(dbObject).results();
        Iterator iterator = results.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        disconnectDB();
    }

    //查看cat_id下面的商品数量
    @Test
    public void doCount5(){
        connectDB();
        GoodDao goodDao = new GoodDao();
        //String语句
//        Iterable results = goodDao.aggregate().group("{_id:'$cat_id',count:{$sum:1}}").results();
//        Iterator iterator = results.iterator();
       // while (iterator.hasNext()){
        //    System.out.println(iterator.next());
       // }
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("_id","$cat_id");
        basicDBObject.put("sum",new BasicDBObject("$sum",1));
        Iterable results1 = goodDao.aggregate().group(basicDBObject).results();
        Iterator iterator1 = results1.iterator();
        while (iterator1.hasNext()){
            System.out.println(iterator1.next());
        }
        disconnectDB();
    }

    //Good集合下的数据总条数
    @Test
    public void doCount6(){
        connectDB();
        GoodDao goodDao = new GoodDao();
        Iterable <DBObject>results = goodDao.aggregate().group("{_id:null,total:{$sum:1}}").results();
        for(DBObject dbObject:results){
            Integer sum = (Integer) dbObject.get("total");
            System.out.println(sum);
        }
        disconnectDB();
    }

    //#查询每个栏目下 价格大于830元的商品个数
    @Test
    public void doCount7(){
        connectDB();
        GoodDao goodDao = new GoodDao();
        //根据查询条件
//        Iterable shop_price = goodDao.aggregate().match(goodDao.query().greaterThan("shop_price", 830)).group("{_id:'$cat_id',sum:{$sum:1}}").results();
//        Iterator iterator = shop_price.iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }

        //根据DBObject对象
//        BasicDBObject object=new BasicDBObject();
//        object.put("shop_price",new BasicDBObject("$gt",830));
//        BasicDBObject basicDBObject = new BasicDBObject();
//        basicDBObject.put("_id","$cat_id");
//        basicDBObject.put("total",new BasicDBObject("$sum",1));
//        Iterable results = goodDao.aggregate().match(object).group(basicDBObject).results();
//        Iterator iterator = results.iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }

        //根据字符串
        Iterable results = goodDao.aggregate().match("{shop_price:{$gt:830}}").group("{_id:'$cat_id',sum:{$sum:1}}").results();
        Iterator iterator = results.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        disconnectDB();
    }
    //#查询每个栏目下 价格大于50元的商品个数 #并筛选出"满足条件的商品个数" 大于等于3的栏目
    @Test
    public void doCount8(){
        connectDB();
        GoodDao goodDao = new GoodDao();
        //DBObject格式
//        BasicDBObject object=new BasicDBObject();
//        object.put("shop_price",new BasicDBObject("$gt",50));
//        BasicDBObject b=new BasicDBObject();
//        b.put("count",new BasicDBObject("$gte",3));
//        Iterable results = goodDao.aggregate().match(object).group("{_id:'$cat_id',count:{$sum:1}}").match(b).results();
//        Iterator iterator = results.iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }

        //String 格式
//        Iterable results = goodDao.aggregate().match("{shop_price:{$gt:50}}").group("{_id:'$cat_id',count:{$sum:1}}").match("{count:{$gte:3}}").results();
//        Iterator iterator = results.iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }

        //查询语句格式
        Iterable results = goodDao.aggregate().match(goodDao.query().greaterThan("shop_price", 50)).group("{_id:'$cat_id',count:{$sum:1}}").match(new BasicDBObject("count",new BasicDBObject("$gte",3))).limit(2).results();
        Iterator iterator = results.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        disconnectDB();
    }
    //查询每个栏目下的库存量,并按库存量排序
    @Test
    public void doCount9(){
        connectDB();
        GoodDao goodDao = new GoodDao();
        //String格式
//        Iterable results = goodDao.aggregate().group("{_id:'$cat_id',count:{$sum:1}}").sort("{count:-1}").results();
//        Iterator iterator = results.iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }

        //DBObject格式
        BasicDBObject basicDBObject=new BasicDBObject();
        //根据goods_number分组
        basicDBObject.put("_id","goods_number");
        basicDBObject.put("count",new BasicDBObject("$sum",1));
        Iterable count = goodDao.aggregate().group(basicDBObject).sort(new BasicDBObject("count", -1)).limit(3).results();
        Iterator iterator = count.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        disconnectDB();
    }
    @Test
    //查询每个栏目的商品平均价格,并按平均价格由高到低排序
    public void doCount10(){
        connectDB();
        GoodDao goodDao = new GoodDao();
        Iterable results = goodDao.aggregate().match("{avg:{$avg:'$shop_price'}}").group("{_id:'$cat_id'}").sort("avg:1").results();
        Iterator iterator = results.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        disconnectDB();
    }
}
