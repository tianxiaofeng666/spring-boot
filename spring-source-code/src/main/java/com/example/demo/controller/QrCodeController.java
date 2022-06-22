package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Student;
import com.example.demo.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shs-cyhlwzytxf
 */
@RestController
@RequestMapping("/qrCode")
public class QrCodeController {

    @Autowired
    CodeService codeService;

    @PostMapping("/createCode")
    public String createCode(){
        Student stu = new Student("李四","女","31032719910512222X");
        return codeService.createCode(stu);
    }
}
