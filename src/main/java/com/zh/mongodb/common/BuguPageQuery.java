package com.zh.mongodb.common;

import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

//分页
public class BuguPageQuery<S> extends BuguQuery<S> {
    public static class Page<S>{
        private int page;
        private int pageSize;
        private int totalRecord;
        private int totalPage;
        private List<S> data;

        public int getPage() {
            return page;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public int getTotalRecord() {
            return totalRecord;
        }

        public List<S> getDatas() {
            return data;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public void setTotalRecord(int totalRecord) {
            this.totalRecord = totalRecord;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public void setData(List<S> data) {
            this.data = data;
        }

        public Page(int page, int pageSize, int totalRecord, List<S> datas) {
            this.page = page;
            this.pageSize = pageSize;
            this.totalRecord = totalRecord;
            this.data = datas;
            this.totalPage = (int)Math.ceil( (double)totalRecord / (double)pageSize );
        }
    }
    public BuguPageQuery(BuguDao<S> dao) {
        super(dao);
    }
    public Page<S> resultsWithPage(){
        int totoal = (int)this.countFast();
        if( totoal == 0 ){
            return new Page<>(this.pageNumber,this.pageSize,totoal,new ArrayList<S>());
        }else{
            List<S> l = this.results();
            if( l == null )
                l =  new ArrayList<>();
            return new Page<>(this.pageNumber,this.pageSize,totoal,l);
        }
    }
    public Page<S> resultsWithPage(boolean fastCount){
        int totoal = 0;
        if( fastCount ){
            totoal = (int)this.countFast();
        }else {
            totoal = (int)this.count();
        }
        if( totoal == 0 ){
            return new Page<>(this.pageNumber,this.pageSize,totoal,new ArrayList<S>());
        }else{
            List<S> l = this.results();
            if( l == null )
                l =  new ArrayList<>();
            return new Page<>(this.pageNumber,this.pageSize,totoal,l);
        }
    }

    //模糊查询
    public BuguQuery<S> regexCaseInsensitive(String key,String regex){
        this.append(key, (String)null, Pattern.compile(regex,Pattern.CASE_INSENSITIVE));
        return this;
    }


}
