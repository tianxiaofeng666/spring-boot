package com.example.springbooteasypoi.service;

import com.example.springbooteasypoi.entity.User;
import com.example.springbooteasypoi.util.ExcelUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shs-cyhlwzytxf
 */
@Service
public class UserService {

    @Resource
    private HttpServletResponse response;

    public void exportUser(){
        try{
            List<User> list = new ArrayList<>();
            User user1 = new User("张三","男",18,"1991-05-12","17521041111","河南省","郑州市");
            User user2 = new User("张三1","男",20,"1994-05-12","17521042222","河南省","南阳市");
            User user3 = new User("张三2","男",22,"1995-05-12","17521043333","上海市","上海市");
            User user4 = new User("张三3","男",24,"1996-05-12","17521044444","河南省","郑州市");
            User user5 = new User("张三4","男",26,"1997-05-12","17521045555","河南省","郑州市");
            User user6 = new User("张三5","男",28,"1998-05-12","17521046666","河南省","郑州市");
            list.add(user1);
            list.add(user2);
            list.add(user3);
            list.add(user4);
            list.add(user5);
            list.add(user6);
            ExcelUtils.exportExcel(list, "用户信息", "用户信息", User.class, String.valueOf(System.currentTimeMillis()), response);
        }catch (Exception e){

        }

    }
}
