package com.example.clouduser.service.impl;


import com.example.clouduser.mapper.UserDao;
import com.example.clouduser.pojo.User;
import com.example.clouduser.service.UserService;
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
