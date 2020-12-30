package com.example.clouduser.pojo;

import lombok.Data;

@Data
public class User {
    private Long id;
    private Long accountId;
    private String username;
    private String password;
    private String email;
    private String mobile;
    private String gmtCreated;

}
