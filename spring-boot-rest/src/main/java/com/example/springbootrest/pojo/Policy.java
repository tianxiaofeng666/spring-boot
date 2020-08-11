package com.example.springbootrest.pojo;


import com.alibaba.excel.annotation.ExcelProperty;

public class Policy {
    @ExcelProperty("保单id")
    private int id;
    @ExcelProperty("保单号")
    private String policyNO;
    @ExcelProperty("归属人")
    private String owner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPolicyNO() {
        return policyNO;
    }

    public void setPolicyNO(String policyNO) {
        this.policyNO = policyNO;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
