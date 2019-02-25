package com.zh.mongodb;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpClientTest {

    @Test
    public void testHttpClinet() {
        String url = "http://www.baidu.com";
        //1.使用默认的配置的httpclient
        CloseableHttpClient client = HttpClients.createDefault();
        //2.使用get方法
        HttpGet httpGet = new HttpGet(url);
        InputStream inputStream = null;
        CloseableHttpResponse response = null;
        try {
            //3.执行请求，获取响应
            response = client.execute(httpGet);

            //看请求是否成功，这儿打印的是http状态码
            System.out.println(response.getStatusLine().getStatusCode());
            //4.获取响应的实体内容，就是我们所要抓取得网页内容
            HttpEntity entity = response.getEntity();

            //5.将其打印到控制台上面
            //方法一：使用EntityUtils
//            if (entity != null) {
//                System.out.println(EntityUtils.toString(entity, "utf-8"));
//            }
//            EntityUtils.consume(entity);

            //方法二  :使用inputStream
            if (entity != null) {
                inputStream = entity.getContent();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);

                }
            }
        } catch (IOException e) {

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
    }

    @Test
    public void testMap()throws UnsupportedEncodingException{
        StringBuilder sb = new StringBuilder();
        //http://域名:httpPort/config/verifyDomain
        String url="http://catl-test.yunext.com:80/config/verifyDomain";
        sb.append(url);
        sb.append("?");
        Map<String,String > parms=new HashMap<>();
        parms.put("mqttPort",Integer.toString(18835));
        Set<Map.Entry<String, String>> entries = parms.entrySet();
        for(Map.Entry<String,String> entry:entries){
            sb.append(URLEncoder.encode(entry.getKey(),"utf-8")).append("=")
                    .append(URLEncoder.encode(entry.getValue(),"utf-8")).append("&");
        }
        System.out.println(sb);

        sb.toString().substring(0, sb.length());
       // System.out.println(substring);

    }
}
