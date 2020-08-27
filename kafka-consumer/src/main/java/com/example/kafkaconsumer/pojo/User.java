package com.example.kafkaconsumer.pojo;

public class User {
    private String message;
    private String mobile;
    private String email;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "主题：" + message + ",手机号：" + mobile + ",邮箱：" + email;
    }
}
