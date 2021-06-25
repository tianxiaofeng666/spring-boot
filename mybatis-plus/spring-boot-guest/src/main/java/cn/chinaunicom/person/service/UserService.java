package cn.chinaunicom.person.service;

import cn.chinaunicom.person.entity.User;
import cn.chinaunicom.person.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    UserMapper userMapper;

    public int save(User user){
        return userMapper.save(user);
    }

    public List<User> getUserList(){
        return userMapper.getUserList();
    }

}
