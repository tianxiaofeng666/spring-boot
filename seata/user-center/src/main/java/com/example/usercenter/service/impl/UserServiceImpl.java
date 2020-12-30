package com.example.usercenter.service.impl;

import com.example.usercenter.mapper.UserDao;
import com.example.usercenter.pojo.User;
import com.example.usercenter.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public boolean addUser(User user) {
        boolean flag = true;
        try {
            userDao.addUser(user);
        }catch (Exception e){
            flag = false;
        }
        return flag;
    }
}
