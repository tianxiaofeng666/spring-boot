package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.util.QRCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author shs-cyhlwzytxf
 */
@Slf4j
@Service
public class CodeService {

    public String createCode(Student stu){
        String content = String.format("%s/%s/%s",stu.getName(),stu.getGender(),stu.getIdCard());
        String words = String.format("%s-%s",stu.getName(),stu.getIdCard());
        return QRCodeUtil.createQRCode(content,words,stu.getIdCard());
    }

}
