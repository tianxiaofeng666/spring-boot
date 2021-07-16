package com.example.clouduser.service;

import com.example.clouduser.pojo.User;

import java.util.List;

public interface UserService {
    public List<User> getAll();

    public boolean addUser(User user);
}
