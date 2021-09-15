package com.example.springbooteasypoi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author shs-cyhlwzytxf
 */
@Data
public class User {

    @Excel(name = "姓名", orderNum = "0")
    private String userName;

    @Excel(name = "性别", orderNum = "1")
    private String sex;

    @Excel(name = "年龄", orderNum = "2")
    private int age;

    @Excel(name = "生日", width = 30, orderNum = "3")
    private String birthday;

    @Excel(name = "手机号", width = 30, orderNum = "4")
    private String mobile;

    @Excel(name = "省份", orderNum = "5")
    private String province;

    @Excel(name = "城市", orderNum = "6")
    private String city;

    public User() {
    }

    public User(String userName, String sex, int age, String birthday, String mobile, String province, String city) {
        this.userName = userName;
        this.sex = sex;
        this.age = age;
        this.birthday = birthday;
        this.mobile = mobile;
        this.province = province;
        this.city = city;
    }

    @Override
    public String toString(){
        return "姓名：" + userName + ",性别：" + sex + ",年龄：" + age +
                ",生日：" + birthday + ",手机号：" + mobile + ",省份：" + province + ",城市：" + city;
    }
}
