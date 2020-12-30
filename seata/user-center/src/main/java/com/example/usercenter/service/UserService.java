package com.example.usercenter.service;

import com.example.usercenter.pojo.User;

import java.util.List;

public interface UserService {
    public List<User> getAll();

    public boolean addUser(User user);
}
