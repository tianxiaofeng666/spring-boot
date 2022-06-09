package com.example.springbootswagger.service;

import com.example.springbootswagger.bean.ResultCode;
import com.example.springbootswagger.exception.BusinessException;
import com.example.springbootswagger.model.request.UserReq;
import com.example.springbootswagger.model.response.UserResp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author shs-cyhlwzytxf
 */
@Service
public class UserServiceImpl {

    public UserResp add(UserReq req){
        UserResp userResp = new UserResp();
        userResp.setName(req.getUserName());
        userResp.setNo("STU111");
        return userResp;
    }

}
