package com.example.springbootmybatis.pojo;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String userName;
    private String password;
    private int age;
    private String gender;
    private String city;
    private String isDelete;
    private String creator;
    private String createTime;
    private String modifier;
    private String updateTime;

}
