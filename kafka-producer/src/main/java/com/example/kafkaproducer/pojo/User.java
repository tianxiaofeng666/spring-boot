package com.example.kafkaproducer.pojo;

import lombok.Data;

@Data
public class User {
    private String message;
    private String mobile;
    private String email;

    public User(String message, String mobile, String email) {
        this.message = message;
        this.mobile = mobile;
        this.email = email;
    }
}
