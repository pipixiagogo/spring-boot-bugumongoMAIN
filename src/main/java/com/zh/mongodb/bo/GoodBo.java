package com.zh.mongodb.bo;

public class GoodBo {
    private double goods_id;
    private double cat_id;
    private String goods_name;
    private double goods_number;
    private double click_count;
    private int shop_price;
    private double  add_time;

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

    public int getShop_price() {
        return shop_price;
    }

    public void setShop_price(int shop_price) {
        this.shop_price = shop_price;
    }

    public double getAdd_time() {
        return add_time;
    }

    public void setAdd_time(double add_time) {
        this.add_time = add_time;
    }

    @Override
    public String toString() {
        return "GoodBo{" +
                "goods_id=" + goods_id +
                ", cat_id=" + cat_id +
                ", goods_name='" + goods_name + '\'' +
                ", goods_number=" + goods_number +
                ", click_count=" + click_count +
                ", shop_price=" + shop_price +
                ", add_time='" + add_time + '\'' +
                '}';
    }
}
