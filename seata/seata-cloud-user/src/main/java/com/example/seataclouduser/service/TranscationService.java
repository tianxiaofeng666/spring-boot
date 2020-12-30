package com.example.seataclouduser.service;

import com.example.seataclouduser.common.CommonException;
import com.example.seataclouduser.common.SeataEnum;
import com.example.seataclouduser.remote.CloudUserRemote;
import com.example.seataclouduser.remote.UserCenterRemote;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class TranscationService {
    /*@Autowired
    private CloudUserRemote cloudUserRemote;

    @Autowired
    private UserCenterRemote userCenterRemote;*/

    @Autowired
    private RestTemplate restTemplate;

    @GlobalTransactional(rollbackFor = Exception.class)
    public void test() throws CommonException {
        String url1 = "http://localhost:4444/addUser";
        String url2 = "http://localhost:3333/addUser";
        boolean res1 = restTemplate.postForObject(url1,null,Boolean.class);
        if(!res1){
            //throw new Exception();
            throw new CommonException(SeataEnum.CLOUD_USER_ERROR.getCode(),SeataEnum.CLOUD_USER_ERROR.getErrorMsg());
        }
        boolean res2 = restTemplate.postForObject(url2,null,Boolean.class);
        if(!res2){
            //throw new Exception();
            throw new CommonException(SeataEnum.USER_CENTER_ERROR.getCode(),SeataEnum.USER_CENTER_ERROR.getErrorMsg());
        }
    }

}
