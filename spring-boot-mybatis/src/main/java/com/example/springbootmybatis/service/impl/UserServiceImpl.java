package com.example.springbootmybatis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootmybatis.mapper.UserDao;
import com.example.springbootmybatis.pojo.User;
import com.example.springbootmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public List<User> findByPage(int from,int num) {
        return userDao.findByPage(from,num);
    }

    @Override
    public int findCount() {
        return userDao.findCount();
    }

    @Override
    public List<User> getUserByCondition(User user) {
        return userDao.getUserByCondition(user);
    }

    @Override
    public List<User> getUserByConditionAndPage(String userName, int from, int num) {
        return userDao.getUserByConditionAndPage(userName,from,num);
    }

    @Override
    public int findCountByCondition(User user) {
        return userDao.findCountByCondition(user);
    }

    @Override
    public void insertOneUser(User user) {
        userDao.insertOneUser(user);
    }

    @Override
    public JSONObject testMulti(int userId) {
        return userDao.testMulti(userId);
    }

    @Override
    public List<JSONObject> testMultiList(List<Integer> userIdList) {
        return userDao.testMultiList(userIdList);
    }
}
