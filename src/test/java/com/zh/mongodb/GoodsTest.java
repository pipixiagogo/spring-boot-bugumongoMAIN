package com.zh.mongodb;

import com.bugull.mongo.BuguQuery;
import com.zh.mongodb.dao.GoodDao;
import com.zh.mongodb.entity.Good;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GoodsTest extends ReplicaSetBaseTest {


    @Test
    public void doQuery(){
        //查询goos_id为32的
        //db.goods.find({goods_id:32});
        connectDB();
        GoodDao goodsDao = new GoodDao();
        BuguQuery<Good> query = goodsDao.query();
        Good goods_id = query.is("goods_id", 32).result();
//        for(Good goods:goodsId){
//            System.out.println(goods);
//        }
        System.out.println(goods_id);
        disconnectDB();
    }

    //查询不属第3栏目的所有商品($ne)
    //db.goods.find({cat_id:{$ne:3}},{goods_id:1,cat_id:1,goods_name:1});
    @Test
    public void doQuery1(){
        connectDB();
        GoodDao good = new GoodDao();
        List<Good> results = good.query().notEquals("cat_id", 3).returnFields("goods_id", "cat_id", "goods_name").results();
        for(Good good1 :results){
            System.out.println(good1);
        }
        disconnectDB();
    }

    //本店价格高于3000元的商品{$gt}
    //db.goods.find({shop_price:{$gt:3000}},{goods_name:1,shop_price:1});
    @Test
    public void doQuery2(){
        connectDB();
        GoodDao goodDao = new GoodDao();
        List<Good> results = goodDao.query().greaterThan("shop_price", 3000).returnFields("goods_name", "shop_price").results();
        for(Good good1 :results){
            System.out.println(good1);
        }
        disconnectDB();
        disconnectDB();
    }

    //本店价格低于或等于100元的商品($lte)
    //db.goods.find({shop_price:{$lte:100}},{goods_name:1,shop_price:1});
    @Test
    public void doQuery3(){
        connectDB();
        GoodDao goodDao = new GoodDao();
        List<Good> results = goodDao.query().lessThanEquals("shop_price", 100).returnFields("shop_price", "goods_name").results();
        for(Good good:results){
            System.out.println(good);
        }

        disconnectDB();
    }
    //取出第4栏目或第11栏目的商品($in)
    //db.goods.find({cat_id:{$in:[4,11]}},{goods_name:1,shop_price:1});
    @Test
    public void doQuery4(){
        connectDB();
        GoodDao goodDao = new GoodDao();
        List<Double>list=new ArrayList<>();
        list.add(4.0);
        list.add(11.0);
        List<Good> results = goodDao.query().in("cat_id", list).returnFields("cat_id", "goods_name").results();
        for(Good good:results){
            System.out.println(good);
        }
        disconnectDB();
    }

    //取出100<=价格<=500的商品($and)
    //db.goods.find({$and:[{price:{$gt:100},{$price:{$lt:500}}}]);
    @Test
    public void doQuery5(){
        connectDB();
        GoodDao goodDao = new GoodDao();
        List<Good> results = goodDao.query().greaterThan("shop_price", 100).lessThan("shop_price", 500).results();
        for(Good good:results){
            System.out.println(good);
        }
        disconnectDB();
    }
    //取出不属于第3栏目且不属于第11栏目的商品($and $nin和$nor分别实现)
    //db.goods.find({$and:[{cat_id:{$ne:3}},{cat_id:{$ne:11}}]},{goods_name:1,cat_id:1})
// db.goods.find({cat_id:{$nin:[3,11]}},{goods_name:1,cat_id:1});
// db.goods.find({$nor:[{cat_id:3},{cat_id:11}]},{goods_name:1,cat_id:1});
    @Test
    public void doQuery6(){
        connectDB();
        GoodDao goodDao = new GoodDao();
        List<Double>list=new ArrayList<>();
        list.add(3.0);
        list.add(11.0);
        List<Good> cat_id = goodDao.query().notIn("cat_id", list).results();
        for(Good good:cat_id){
            System.out.println(good);
        }
        disconnectDB();
    }
    //取出价格大于100且小于300,或者大于4000且小于5000的商品()
    // db.goods.find({$or:[{$and:[{shop_price:{$gt:100}},{shop_price:{$lt:300}}]},
    // {$and:[{shop_price:{$gt:4000}},{shop_price:{$lt:5000}}]}]},{goods_name:1,shop_price:1});
    @Test
    public void doQuery7(){
        connectDB();
        GoodDao goodDao = new GoodDao();
        List<Good> results = goodDao.query().or(goodDao.query().greaterThan("shop_price", 100).lessThan("shop_price", 300),goodDao.query().greaterThan("shop_price",4000).lessThan("shop_price",5000)).results();
        for(Good good:results){
            System.out.println(good);
        }
        disconnectDB();
    }

    //:取出goods_id%5 == 1, 即,1,6,11,..这样的商品
    //db.goods.find({goods_id:{$mod:[5,1]}});
    @Test
    public void doQuery8(){
        connectDB();
        GoodDao goodDao = new GoodDao();
        List<Good> goods_id = goodDao.query().mod("goods_id", 5, 1).results();
        for(Good good:goods_id){
            System.out.println(good);
        }
        disconnectDB();
    }

    //取出有age属性的文档
//db.stu.find({age:{$exists:1}});
//    含有age属性的文档将会被查出
    @Test
    public void doQuery9(){
        connectDB();
        GoodDao goodDao = new GoodDao();
        long count = goodDao.count();
        System.out.println(count);
        List<Good> goods_id = goodDao.query().existsField("goods_id").results();
        for(Good good :goods_id){
            System.out.println(good);
        }
        disconnectDB();
    }
    //时间戳转日期
    @Test
    public void transToTimes(){
        long timeStamp = System.currentTimeMillis();//获取当前时间戳,也可以是你自已给的一个随机的或是别人给你的时间戳(一定是long型的数据)
        long time =  1550734354*1000L;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
        String sd = sdf.format(new Date(time));   // 时间戳转换成时间
        System.out.println(sd);//打印出你要的时间
    }

    @Test
    public void testQuery10(){
        connectDB();
        GoodDao goodDao = new GoodDao();
        //模糊查询
        List<Good> results = goodDao.query().regexCaseInsensitive("goods_name", "摩托").results();
        for(Good good:results){
            System.out.println(good);
        }
        disconnectDB();
    }


}
