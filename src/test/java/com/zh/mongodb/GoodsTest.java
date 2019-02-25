package com.zh.mongodb;

import com.bugull.mongo.BuguQuery;
import com.zh.mongodb.bo.GoodBo;
import com.zh.mongodb.common.BuguPageQuery;
import com.zh.mongodb.dao.GoodDao;
import com.zh.mongodb.entity.Good;
import com.zh.mongodb.util.ByteUtil;
import com.zh.mongodb.util.Md5;
import com.zh.mongodb.util.PlusArrayUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
   // 取出100<=价格<=500的商品($and)
    @Test
    public void testQuery11(){
        connectDB();
        GoodDao goodDao = new GoodDao();
        BuguPageQuery<Good> goodQuery = goodDao.pageQuery();
        goodQuery.pageNumber(1).pageSize(5);
        BuguPageQuery.Page<Good> goodPage = goodQuery.resultsWithPage();
        List<Good> datas = goodPage.getDatas();
        List<GoodBo> bo=new ArrayList<>();
        for(Good good:datas){
            GoodBo goodBo = new GoodBo();
            System.out.println(good);
            BeanUtils.copyProperties(good,goodBo);
            bo.add(goodBo);
        }
        for(GoodBo goodBo:bo){
            System.out.println(goodBo);
        }
        disconnectDB();
    }

    @Test
    public void writeConfig(){
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeIntLE(67);
        byte[] bytes=new byte[buffer.readableBytes()];
        buffer.readBytes(bytes);
        for(byte b1:bytes){
            System.out.println(b1);
        }
    }
    public static final String APICLIENT_CERT_P12 = "totalCanid.txt";
    @Test
    public void getTotalCANids(){
        InputStream fileInputStream=null;
        BufferedReader reader =null;
        try {
            fileInputStream = GoodsTest.class.getClassLoader().getResourceAsStream(APICLIENT_CERT_P12);

            reader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line=reader.readLine();
            if(line.length()!=10){
                System.out.println(false);
            }
            System.out.println(line.substring(2));
                byte[] bytes = ByteUtil.parseHexStringToArray(line.substring(2));
                for(byte b:bytes){
                    System.out.println(b);
                }
            long l = ByteUtil.toUnsignedInt(bytes);
            System.out.println(l);

        }catch (Exception e){
                     System.out.println(321);
            }finally {
                try {
                    reader.close();
                    fileInputStream.close();
                }catch (Exception e){
                    System.out.println(123);
                }
        }

    }

    @Test
    public void testChange(){
        byte[] bytes= new byte[]{4,1,-18,65};
        byte[] bytes1=new byte[]{1,2,3,4};
        byte[] combine = PlusArrayUtils.combine(bytes, bytes1);
        for(byte b:combine){
            System.out.print(b+"---");
        }
        System.out.println();
        System.out.print(combine.length);
        byte[] bytes2 = MergerArray(bytes, bytes1);
        System.out.println();
        for(byte b:bytes2){
            System.out.print(b+"~~~~~");
        }
        System.out.println();
        System.out.println(bytes2.length);

        int i = byteArrayToInt(bytes);//67235493
        System.out.println(i);
    }

    //byte 数组与 int 的相互转换
    public static int byteArrayToInt(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static byte[] MergerArray(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) {
        byte[] arrayOfByte = new byte[paramArrayOfByte1.length + paramArrayOfByte2.length];
        System.arraycopy(paramArrayOfByte1, 0, arrayOfByte, 0, paramArrayOfByte1.length);
        System.arraycopy(paramArrayOfByte2, 0, arrayOfByte, paramArrayOfByte1.length, paramArrayOfByte2.length);
        return arrayOfByte;
    }

    public static final int MAGIC_NUM = ByteUtil.toInt( new byte[]{(byte) 0xfc, (byte) 0xfc, (byte) 0xfc, (byte) 0xfc} );
    private static final byte[] FILE_ID = new byte[]{0x00,0x00};
    private static final byte[] SERVER_ID = new byte[]{0x00,0x00};
    private static final byte[] CAN_COLLECT_CONFIG_ID = new byte[]{0x02,0x10};
    private static final short CAN_COLLECT_CONFIG_LEN = 15;
    @Test
    public void config(){
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt( MAGIC_NUM );
//        if( false ){
//            //83
//            buf.writeIntLE( (int)getFileSize() );
//        }else {
            //67 小端序写入 无扩展 fileSize + domain.getBytes().length= 87
//            buf.writeIntLE( (int)getFileSizeWithoutExtends() );
//        System.out.println(getFileSizeWithoutExtends());
       // }

        buf.writeBytes( FILE_ID );
        //构建服务器基本参数 小端序写入
        buf.writeBytes( SERVER_ID );
        buf.writeShortLE(30);
        buf.writeShortLE( 20 );
        buf.writeShortLE( 300 );
        buf.writeShortLE( 60 );
        buf.writeShortLE( 80 );
        buf.writeShortLE(18835);
        buf.writeShortLE(20 );
        buf.writeBytes("catl-test.yunext.com".getBytes()  );
//        if( extendsConfig ){
//            //构建基本参数
//            buf.writeBytes( BASE_ID );
//            buf.writeShortLE( getBaseDataLen() );
//            buf.writeShortLE( this.canCollectInterval );
//            buf.writeShortLE( this.freConvCollectInterval );
//            if( this.freConvSwitch ){
//                buf.writeBytes( FRE_COL_ON_BYTE );
//            }else{
//                buf.writeBytes( FRE_COL_OFF_BYTE );
//            }
//            //构建采集对象
//            buf.writeBytes( COLLECT_OBJ_ID );
//            buf.writeShortLE( getCollectObjDataLen() );
//            buf.writeShortLE( getCollectNum() );
//            if( this.objs != null ){
//                for( CollectObject co : objs ){
//                    buf.writeIntLE((int) co.getCanid());
//                    buf.writeShortLE( co.getCollectTime() );
//                    buf.writeBytes( RETAIN_BYTES );
//                }
//            }
//        }
        buf.writeBytes( CAN_COLLECT_CONFIG_ID );
        buf.writeShortLE(getCanCollectConfigLen());
        buf.writeShortLE(10);//概要数据采集间隔
        buf.writeShortLE(4);//概要数据变频采集间隔
        buf.writeShortLE(1);//调试数据采集间隔
        buf.writeShortLE(60);//详细数据采集间隔
        buf.writeShortLE(10);//扩展信息采集间隔
        fillBoolean(buf, true);//概要数据采集配置
        fillBoolean(buf,false);//详细数据采集配置
        fillBoolean(buf, false);//扩展数据采集配置
        fillBoolean(buf,false);//调试数据采集配置
        fillBoolean(buf,true);//变频采集是否开启
        byte[] file = new byte[ buf.readableBytes() ];
        buf.readBytes( file );
//        String s = ByteUtil.toHexString(file);
//        System.out.println(s);

        Md5 md5 = Md5.getInstance();
        byte[] bytes = md5.md5_16_byte(file);
        if(bytes.length<24){
            byte[] remain=new byte[24-bytes.length];
            bytes= PlusArrayUtils.combine(bytes, remain);
        }
        byte[] combine = PlusArrayUtils.combine(bytes, file);
        String s = ByteUtil.toHexString(combine);
        System.out.println(s);
    }

    @Test
    public void testMD5(){
        Md5 instance = Md5.getInstance();
        byte[] b=new byte[]{3,2,1};
        byte[] bytes = instance.md5_16_byte(b);
        String s = ByteUtil.toHexString(bytes);
        System.out.println(s);
        byte[] b1=new byte[]{3,2,1};
        byte[] bytes1 = instance.md5_16_byte(b1);
        String s1 = ByteUtil.toHexString(bytes1);
        System.out.println(s1);
    }


    private void fillBoolean(ByteBuf buf, boolean flag){
        if( flag ){
            buf.writeByte(1);
        }else {
            buf.writeByte(0);
        }
    }
    public long getFileSize() {
        //83
        long fileSize = 83;
        return fileSize;
    }
    private short getCanCollectConfigLen(){
        return CAN_COLLECT_CONFIG_LEN;
    }
    public long getFileSizeWithoutExtends(){
        //67
        long fileSize = 67;
        //domain 20
        fileSize = fileSize + "catl-test.yunext.com".getBytes().length;

        return fileSize;
    }
//    public int getServerDataLen(){
//        if( domain == null ){
//            return SERVER_DATA_LEN_MIN;
//        }else {
//            return SERVER_DATA_LEN_MIN + domain.getBytes().length;
//        }
//    }

    @Test
    public void doLength(){
        System.out.println("catl-test.yunext.com".getBytes().length);
    }


}
