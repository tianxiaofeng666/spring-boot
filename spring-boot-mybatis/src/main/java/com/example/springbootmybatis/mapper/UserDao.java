package com.example.springbootmybatis.mapper;

import com.example.springbootmybatis.pojo.User;

import java.util.List;

public interface UserDao {

    /**
     * 获取所有用户角色
     */
    public List<User> findAll();
}
