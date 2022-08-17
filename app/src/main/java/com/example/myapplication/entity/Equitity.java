package com.example.myapplication.entity;

import java.io.Serializable;
import java.util.List;

public class Equitity implements Serializable {

    private List<EquitiesBean> equities;

    public List<EquitiesBean> getEquities() {
        return equities;
    }

    public void setEquities(List<EquitiesBean> equities) {
        this.equities = equities;
    }
}
