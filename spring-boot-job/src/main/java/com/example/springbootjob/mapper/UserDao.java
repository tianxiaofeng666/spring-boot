package com.example.springbootjob.mapper;

import com.example.springbootjob.pojo.User;
import org.apache.ibatis.annotations.Insert;

public interface UserDao {

    @Insert("insert into t_user(name,gender,age,mobile,gmt_created) values(#{name},#{gender},#{age},#{mobile},#{gmtCreated})")
    public void addUser(User user);
}
