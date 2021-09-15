package com.example.springbooteasypoi.controller;

import com.example.springbooteasypoi.entity.User;
import com.example.springbooteasypoi.service.UserService;
import com.example.springbooteasypoi.util.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shs-cyhlwzytxf
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/exportUser")
    public void exportUser(){
        userService.exportUser();
    }

    @PostMapping("/importUser")
    public List<User> importUser(@RequestParam("file")MultipartFile file) throws Exception{
        List<User> list = ExcelUtils.importExcel(file,1,1,User.class);
        System.out.println(list.size());
        list.stream().map(user -> {
            System.out.println(user.toString());
            return user.getUserName();
        }).collect(Collectors.toList());
        return list;
    }
}
