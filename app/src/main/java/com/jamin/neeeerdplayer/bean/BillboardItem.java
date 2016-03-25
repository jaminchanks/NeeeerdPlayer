package com.jamin.neeeerdplayer.bean;

import java.io.Serializable;

/**
 * Created by jamin on 16-3-21.
 */
public class BillboardItem implements Serializable {
    public BillboardItem(String categoryName, String no1, String no2, String no3) {
        this.categoryName = categoryName;
        this.no1 = no1;
        this.no2 = no2;
        this.no3 = no3;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getNo1() {
        return no1;
    }

    public void setNo1(String no1) {
        this.no1 = no1;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getNo3() {
        return no3;
    }

    public void setNo3(String no3) {
        this.no3 = no3;
    }

    String categoryName;
    String no1;
    String no2;
    String no3;

}
