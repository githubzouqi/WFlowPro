package com.example.myapplication.entity;

import java.io.Serializable;

public class EquitiesBean implements Serializable {

    private String productId;
    private String startDate;
    private String endDate;

    public EquitiesBean() {
    }

    public EquitiesBean(String productId, String startDate, String endDate) {
        this.productId = productId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
