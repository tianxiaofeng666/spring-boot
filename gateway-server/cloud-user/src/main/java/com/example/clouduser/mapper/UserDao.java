package com.example.clouduser.mapper;

import com.example.clouduser.pojo.User;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface UserDao {
    public List<User> getAll();

    @Insert("insert into t_user(account_id,user_name,password,email,mobile) values(#{accountId},#{username},#{password},#{email},#{mobile})")
    public void addUser(User user);
}
