package com.example.springbootdemo.service;

import com.example.springbootdemo.pojo.User;
import com.example.springbootdemo.util.CommonUtil;
import com.example.springbootdemo.util.HttpUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class DemoService {

    public String addPer(MultipartFile file, User user) throws Exception {
        Map<String, Object> param = new HashMap();
        param.put("Name",user.getName());
        param.put("Code",user.getCode());
        param.put("Sex",user.getSex());
        param.put("Depart","iii");
        Map<String, File> fileMap = new HashMap<>();
        fileMap.put("file", CommonUtil.multipartFileToFile(file));

        String cookie = "JSESSIONID=62AEED9203AAA9E235E2EA8E80BB19B5; Path=/fastgate; HttpOnly";
        String url = "https://jyhcu.com:18088/video/fastgate/person";
        String res = HttpUtil.postFormDataWithCookie(url,param,fileMap,cookie);
        return res;
    }
}
