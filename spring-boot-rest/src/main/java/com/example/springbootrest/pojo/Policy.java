package com.example.springbootrest.pojo;


import com.alibaba.excel.annotation.ExcelProperty;

public class Policy {
    @ExcelProperty(value = {"保单id","保单id"},index = 0)
    private int id;
    @ExcelProperty(value = {"保单信息","保单号"},index = 1)
    private String policyNO;
    @ExcelProperty(value = {"保单信息","归属人"},index = 2)
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
