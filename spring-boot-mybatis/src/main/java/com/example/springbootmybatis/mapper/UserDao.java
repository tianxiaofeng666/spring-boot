package com.example.springbootmybatis.mapper;

import com.example.springbootmybatis.pojo.User;

import java.util.List;

public interface UserDao {

    /**
     * 获取所有用户角色
     */
    public List<User> findByPage(int from,int num);

    public int findCount();

    public List<User> getUserByCondition(User user);

    public List<User> getUserByConditionAndPage(String userName,int from,int num);

    public int findCountByCondition(User user);

    public void insertOneUser(User user);
}
