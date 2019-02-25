package com.zh.mongodb;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zh.mongodb.dao.DeviceDao;
import com.zh.mongodb.dao.GoodDao;
import org.junit.Test;

import java.util.*;

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
        //字符串查询
//        Iterable results = goodDao.aggregate().group("{_id:'$cat_id',avg:{$avg:'$shop_price'}}").sort("avg:1").results();
//        Iterator iterator = results.iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }

        //DBObject对象查询
//        BasicDBObject basicDBObject = new BasicDBObject();
//        basicDBObject.put("_id","$cat_id");
//        basicDBObject.put("avg",new BasicDBObject("$avg","$shop_price"));
//        BasicDBObject object=new BasicDBObject();
//        object.put("avg",1);
//        Iterable results = goodDao.aggregate().group(basicDBObject).sort(object).results();
//        Iterator iterator = results.iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }

        //字符串加DBObject查询
//        BasicDBObject basicDBObject=new BasicDBObject();
//        basicDBObject.put("avg",1);
//        Iterable results = goodDao.aggregate().group("{_id:'$cat_id',avg:{$avg:'$shop_price'}}").sort(basicDBObject).results();
//        Iterator iterator = results.iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }

        //DBObject加字符串查询
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("_id","$cat_id");
        basicDBObject.put("avg",new BasicDBObject("$avg","$shop_price"));
        Iterable results = goodDao.aggregate().group(basicDBObject).sort("avg:1").results();
        Iterator iterator = results.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        disconnectDB();
    }
    //查询每个栏目下价格高于50元的商品数量
    @Test
    public void doCount11(){
        connectDB();
        GoodDao goodDao = new GoodDao();
        Iterable results = goodDao.aggregate().match("{shop_price:{$gt:50}}").group("{_id:'$cat_id',sum:{$sum:1}}").results();
        Iterator iterator = results.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        disconnectDB();
    }

    @Test
    public void doCount12(){
        connectDB();
        DeviceDao deviceDao = new DeviceDao();
        //总设备
        long count = deviceDao.count();
        System.out.println(count);
        //在线设备
        long online = deviceDao.query().is("online", true).countFast();
        System.out.println(online);
        //离线设备
        long unline = deviceDao.query().is("online", false).countFast();
        System.out.println(unline);
        disconnectDB();
    }

    @Test
    public void testTime(){
        Calendar date = Calendar.getInstance();
        date.set(Calendar.MINUTE,0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        System.out.println(date.getTime());
    }

    @Test
    public void doCount13(){
        connectDB();
        DeviceDao deviceDao = new DeviceDao();
        Iterable <DBObject>results = deviceDao.aggregate().match(deviceDao.query().
                greaterThan("registDate", getStartTimeOfYear(2019))
                .lessThan("registDate", getEndTimeOfYear(2019)))
                .project("{yearMonthDay:{$dateToString:{format: '%Y-%m', date: '$registDate'}}}")
                .group("{_id:'$yearMonthDay',sum:{$sum:1}}").results();
        for(DBObject object:results){
            String yearMonthDay = (String) object.get("_id");
            Integer sum = (Integer) object.get("sum");
            System.out.println(yearMonthDay+"---"+sum);
        }
        disconnectDB();
    }
    @Test
    public void doCount14(){
//        double a=0.1;
//        double ceil = Math.ceil(a);
//        System.out.println(ceil);
        int base = 7;
        int aim = 0;
        int from = 0;
        int[] inc = new int[]{0,0,0,2,6};
        for( int i = 1; i < 8; i++ ){
            base += inc[ (i - 1)%(inc.length) ];
            aim += base;
            //System.out.println(aim);
            if(i==7){
                System.out.println("i==7"+aim);
            }else {
                //System.out.println(aim);
            }
            from = aim;
            System.out.println(from);
        }
    }
    @Test
    public void doCount15(){
       // { "_id" : { "$oid" : "5c3300f3431bee16445c3e4b" },
        // "state" : "USED", "deviceName" : "f30c8e5a68594e77a87b46be4a7bb0d1",
        // "deviceSecret" : "U0LBIuJ31E1H", "rdbsn" : "pipixia",
        // "upgradeStatus" : false, "bmusns" : "12345678|12345678|123456789",
        // "successRecord" : { "$numberLong" : "0" }, "online" : false,
        // "defaultGroup" : true,
        // "rdbstatus" : { "error" : false, "flashRemain" : { "$numberLong" : "16909060" },
        // "flashUsed" : { "$numberLong" : "185339150" },
        // "updateTime" : { "$date" : 1548646510835 } },
        // "registDate" : { "$date" : 1546846451961 },
        // "configState" : "NORMAL", "restartStatus" : "RESTART_NORMAL",
        // "cleanFlushStatus" : "CLEAN_NORMAL", "factoryCode" : "pi",
        // "isAccordance" : true, "geographicPosition" : { }, "currentIp" : null,
        // \"onlineTime" : null, "startConfigTime" : null, "lastIp" : "192.168.1.40",
        // "lastOfflineTime" : { "$date" : 1550042185297 },
       // 1550813269000
        // "lastOnlineTime" : { "$date" : 1550041941973 }, "statusUpdateTime" : { "$date" : 1548646510835 }
        connectDB();
        DeviceDao deviceDao = new DeviceDao();
        Iterable<DBObject> results =
                deviceDao.aggregate()
                        .match(deviceDao.query().is("online", false).existsField("lastOnlineTime"))
                        //new Date减去$lastOfflineTime 的绝对值
                        //$divide 返回 { $subtract:[ new Date(),'$lastOfflineTime' ] }除以(一天的毫秒数)86400 000的绝对值
                        //$ceil 0.1--> 1.0
                        .project("{'time':{$ceil:{$divide:[{$subtract:[new Date(),'$lastOfflineTime']},86400000]}}}").results();
//                        .group("{_id:null,sum:{$sum:1}}");
        for(DBObject object:results){
            long sum = Double.valueOf(object.get("time").toString()).longValue();
            System.out.println(sum);
        }
        long l = System.currentTimeMillis() - 1550042185297L;
        System.out.println(l);

//        Iterator iterator = results.iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }
        disconnectDB();
    }

    @Test
    public void doCount16(){
        StringBuilder sb = new StringBuilder();
        //{ 'num':
        // {$switch:
        // {branches:[
        // {case :{$and:[ {$gt:['$time',0]},{$lte:['$time',7]} ]},then:7 },
        // { case :{$and:[ {$gt:['$time',7]},{$lte:['$time',14]} ]},then:14 },
        // { case :{$and:[ {$gt:['$time',14]},{$lte:['$time',21]} ]},then:21 },
        // { case :{$and:[ {$gt:['$time',21]},{$lte:['$time',30]} ]},then:30 },
        // { case :{$and:[ {$gt:['$time',30]},{$lte:['$time',45]} ]},then:45 },
        // { case :{$and:[ {$gt:['$time',45]},{$lte:['$time',60]} ]},then:60 },
        // ],default:75}} }
        //switch执行一个指定的表达式并从控制中终端
        sb.append("{ 'num':{$switch:{branches:[");
        int base = 7;
        int aim = 0;
        int from = 0;
        int[] inc = new int[]{0,0,0,2,6};
        for( int i = 1; i < 8; i++ ){
            base += inc[ (i - 1)%(inc.length) ];
            aim += base;
            if( i == 7 ){
                //得出aim为60天
                sb.append("],default:" + aim + "}} }");
            }else {
                //0-7,7-14,14-21,21-30,30-45
                //then 返回aim
                sb.append("{ case :{$and:[ {$gt:['$time',"+ from +"]},{$lte:['$time',"+ aim +"]} ]},then:"+ aim +" },");
            }
            from = aim;
        }
        System.out.println(sb.toString());
    }
    private Date getStartTimeOfYear(Integer year) {
        Calendar yearStart = Calendar.getInstance();
        yearStart.set( Calendar.YEAR,year );
        yearStart.set( Calendar.MONTH,0 );
        yearStart.set( Calendar.DATE,1 );
        yearStart.set(Calendar.HOUR_OF_DAY, 0);
        yearStart.set(Calendar.MINUTE, 0);
        yearStart.set(Calendar.SECOND, 0);
        yearStart.set(Calendar.MILLISECOND, 0);
        return yearStart.getTime();
    }
    private Date getEndTimeOfYear(Integer year) {
        Calendar yearEnd = Calendar.getInstance();
        yearEnd.set( Calendar.YEAR,year + 1 );
        yearEnd.set( Calendar.MONTH,0 );
        yearEnd.set( Calendar.DATE,1 );
        yearEnd.set(Calendar.HOUR_OF_DAY, 0);
        yearEnd.set(Calendar.MINUTE, 0);
        yearEnd.set(Calendar.SECOND, 0);
        yearEnd.set(Calendar.MILLISECOND, -1);
        return yearEnd.getTime();
    }


}
