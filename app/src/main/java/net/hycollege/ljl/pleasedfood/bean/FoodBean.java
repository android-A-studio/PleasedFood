package net.hycollege.ljl.pleasedfood.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class FoodBean {
    @Id
    private String name_f="";
    private int price_f=0;
    private String picture_f="";
    private String details_f="";
    private String utime_f;
    @Generated(hash = 255842417)
    public FoodBean(String name_f, int price_f, String picture_f, String details_f,
            String utime_f) {
        this.name_f = name_f;
        this.price_f = price_f;
        this.picture_f = picture_f;
        this.details_f = details_f;
        this.utime_f = utime_f;
    }
    @Generated(hash = 895705851)
    public FoodBean() {
    }
    public String getName_f() {
        return this.name_f;
    }
    public void setName_f(String name_f) {
        this.name_f = name_f;
    }
    public int getPrice_f() {
        return this.price_f;
    }
    public void setPrice_f(int price_f) {
        this.price_f = price_f;
    }
    public String getPicture_f() {
        return this.picture_f;
    }
    public void setPicture_f(String picture_f) {
        this.picture_f = picture_f;
    }
    public String getDetails_f() {
        return this.details_f;
    }
    public void setDetails_f(String details_f) {
        this.details_f = details_f;
    }
    public String getUtime_f() {
        return this.utime_f;
    }
    public void setUtime_f(String utime_f) {
        this.utime_f = utime_f;
    }

    @Override
    public String toString() {
        return "FoodMenu [name_f=" + name_f + ", price_f=" + price_f
                + ", picture_f=" + picture_f + ", details_f=" + details_f
                + ", utime_f=" + utime_f + "]";
    }
}
