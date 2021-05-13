package com.example.springbootmybatis.service;

import com.example.springbootmybatis.pojo.User;

import java.util.List;

public interface UserService {

    public List<User> findByPage(int from,int num);

    public int findCount();

    public List<User> getUserByCondition(User user);

    public List<User> getUserByConditionAndPage(String userName,int from,int num);

    public int findCountByCondition(User user);

    public void insertOneUser(User user);

}
