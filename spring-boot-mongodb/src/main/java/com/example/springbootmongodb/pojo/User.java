package com.example.springbootmongodb.pojo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "t_user")
public class User {
    private String name;
    private String mobile;

    public User() {
    }

    public User(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
