package com.example.springbootredislock.pojo;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "t_commodity_store")
public class CommodityStore {

    private String commodityId;

    private int num;

    public CommodityStore() {
    }

    public CommodityStore(String commodityId, int num) {
        this.commodityId = commodityId;
        this.num = num;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
