package com.swz637.Bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @ author: lsq637
 * @ since: 2022-09-21 16:09:07
 * @ describe:
 */
public class Goods implements Serializable {
    private int id;
    private String productName;
    private Date productionDate;//物品生产日期
    private int expirationDate;//保质期
    private String productImg;//物品图片的引用地址

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productionDate=" + productionDate +
                ", expirationDate=" + expirationDate +
                ", productImg='" + productImg + '\'' +
                ", remainDays='" + remainDays + '\'' +
                '}';
    }

    public String getRemainDays() {
        return remainDays;
    }

    public void setRemainDays(String remainDays) {
        this.remainDays = remainDays;
    }

    private String remainDays;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public int getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(int expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }
}
