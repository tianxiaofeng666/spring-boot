package com.example.springbootjob.pojo;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String gender;
    private String age;
    private String mobile;
    private String gmtCreated;

    @Override
    public String toString(){
        return "姓名：" + name +",性别：" + gender + ",年龄：" + age + ",手机号：" + mobile;
    }
}
