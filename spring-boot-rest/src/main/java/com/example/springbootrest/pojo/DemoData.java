package com.example.springbootrest.pojo;


import com.alibaba.excel.annotation.ExcelProperty;

public class DemoData {
    @ExcelProperty("编号")
    private int id;
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("年龄")
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    @ExcelProperty("性别")
    private String sex;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @ExcelProperty("城市")
    private String city;


}
