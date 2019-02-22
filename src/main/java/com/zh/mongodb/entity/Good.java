package com.zh.mongodb.entity;

import com.bugull.mongo.SimpleEntity;
import com.bugull.mongo.annotations.Entity;

@Entity
public class Good extends SimpleEntity{
    private double goods_id;
    private double cat_id;
    private String goods_name;
    private double goods_number;
    private double click_count;
    private double shop_price;
    private String  add_time;


    @Override
    public String toString() {
        return "Good{" +
                "goods_id=" + goods_id +
                ", cat_id=" + cat_id +
                ", goods_name='" + goods_name + '\'' +
                ", goods_number=" + goods_number +
                ", click_count=" + click_count +
                ", shop_price=" + shop_price +
                ", add_time='" + add_time + '\'' +
                '}';
    }

    public double getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(double goods_id) {
        this.goods_id = goods_id;
    }

    public double getCat_id() {
        return cat_id;
    }

    public void setCat_id(double cat_id) {
        this.cat_id = cat_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public double getGoods_num() {
        return goods_number;
    }

    public void setGoods_num(double goods_number) {
        this.goods_number = goods_number;
    }

    public double getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(double goods_number) {
        this.goods_number = goods_number;
    }

    public double getClick_count() {
        return click_count;
    }

    public void setClick_count(double click_count) {
        this.click_count = click_count;
    }

    public double getShop_price() {
        return shop_price;
    }

    public void setShop_price(double shop_price) {
        this.shop_price = shop_price;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
