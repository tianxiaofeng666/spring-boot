package com.example.springbootesdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootesdemo.pojo.User;
import com.example.springbootesdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 数据添加进es
     * @return
     */
    @RequestMapping("/saveAll")
    public String saveAll(){
        List<User> list = new ArrayList<>();
        list.add(new User(1L,"张三","1991-01-01","男","北京"));
        list.add(new User(2L,"李四","1992-01-01","女","上海"));
        list.add(new User(3L,"李四三","1993-01-01","男","广州"));
        list.add(new User(4L,"张三","1994-01-01","女","深圳"));
        userService.saveAll(list);
        return "添加成功！";
    }

    /**
     * 从es查询数据
     */
    @RequestMapping("/query")
    public List<User> query(){
        List<User> userList = userService.findByNameLike("李四");
        return userList;

    }

    @RequestMapping("/findByName")
    public List<User> findByName(@RequestBody JSONObject json){
        String name = json.getString("name");
        List<User> userList = userService.findByName(name);
        return userList;

    }

    @RequestMapping("/getAll")
    public Iterator<User> getAll(){
        Iterator<User> list = userService.findAll();
        return list;
    }

    @RequestMapping("/delete")
    public void  delete(@RequestBody JSONObject json){
        Long id = json.getLong("id");
        userService.deleteById(id);
    }

    @RequestMapping("/findById")
    public User findById(@RequestBody JSONObject json){
        Long id = json.getLong("id");
        return userService.findById(id);
    }

    /**
     * 分页
     */
    @RequestMapping("/findByNamePage")
    public List<User> findByNamePage(@RequestBody JSONObject json){
       String name = json.getString("name");
       int currentPage = json.getInteger("currentPage");
       int pageSize = json.getInteger("pageSize");
       Pageable pageable = PageRequest.of(currentPage-1,pageSize, Sort.by(Sort.Order.asc("id")));
        Page<User> items = userService.findByName(name, pageable);

        long total = items.getTotalElements();
        int totalPage = items.getTotalPages();
        int nowPage = items.getNumber() + 1;
        int size = items.getSize();
        System.out.println("总条数 = "+total);
        System.out.println("总页数 = "+totalPage);
        System.out.println("当前页 = "+nowPage);
        System.out.println("每页大小 = "+size);

        List<User> list = new ArrayList<>();
        for (User user: items) {
            list.add(user);
        }
        return list;
    }

    @RequestMapping("/deleteIndex")
    public void deleteIndex(){
        userService.deleteIndex();
    }
}
