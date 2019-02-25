package com.zh.mongodb.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class ResHelper<T> {
    private static final Logger log = LoggerFactory.getLogger(ResHelper.class);
    public static final int SUCCESS = 1;
    public static final int ERROR = 0;
    //1:成功
    //0：失败
    private int code;

    private String msg;

    private T data;
    //检查排序
//    public static boolean checkOrder(Integer order, String orderField, String table) {
//        //两个都没传
//        if( StringUtil.isEmpty( orderField ) && order == null ){
//            return true;
//        }
//        if( StringUtil.isEmpty( orderField ) && order != null ){
//            return false;
//        }
//        if( !StringUtil.isEmpty( orderField ) && order == null ){
//            return false;
//        }
//        if( !Const.DESC.equals( order ) && !Const.ASC.equals( order ) ){
//            return false;
//        }
//        if( !Const.SORT_FIELDS.get( table ).contains( orderField ) ){
//            return false;
//        }
//        return true;
//    }

    //参数错误
//    public static void pamIllHTML(HttpServletResponse response){
//        errorHTML( response, PARAM_ILL);
//    }
    //
    public static void errorHTML(HttpServletResponse response, String errorCode) {
        response.setContentType("text/html; charset=UTF-8");
        Writer writer = null;
        try {
            writer = response.getWriter();
            writer.write( errorCode );
        }catch (Exception e){
            log.error("响应数据写出异常",e);
        }finally {
            if( writer != null ){
                try {
                    writer.close();
                } catch (IOException e) {
                    log.error("关闭响应输出流异常",e);
                }
            }
        }
    }

    public boolean isSuccess(){
        return SUCCESS == code && data != null;
    }

    private ResHelper(){}

    public static <S> ResHelper<S> success( String msg, S s ){
        ResHelper<S> helper = new ResHelper();
        helper.setCode( SUCCESS );
        helper.setData( s );
        helper.setMsg( msg );
        return helper;
    }




    public static ResHelper success(String msg ){
        ResHelper helper = new ResHelper();
        helper.setCode( SUCCESS );
        helper.setMsg( msg );
        return helper;
    }

    public static ResHelper error( String msg ){
        ResHelper helper = new ResHelper();
        helper.setCode( ERROR );
        helper.setMsg( msg );
        return helper;
    }

    //public static ResHelper pamIll() {
//        return ResHelper.error( PARAM_ILL );
//    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

}
