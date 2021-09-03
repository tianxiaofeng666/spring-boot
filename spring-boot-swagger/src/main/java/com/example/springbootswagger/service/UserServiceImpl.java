package com.example.springbootswagger.service;

import com.example.springbootswagger.bean.ResultCode;
import com.example.springbootswagger.exception.BusinessException;
import com.example.springbootswagger.model.request.UserReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    public void add(UserReq req){
        if(StringUtils.isBlank(req.getComment())){
            throw new BusinessException(ResultCode.VALIDATE_FAILED);
        }
        int i = 1 / 0;
    }

}
