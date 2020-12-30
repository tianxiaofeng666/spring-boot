package com.example.seataclouduser.controller;

import com.example.seataclouduser.common.CommonException;
import com.example.seataclouduser.remote.CloudUserRemote;
import com.example.seataclouduser.service.TranscationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class TranscationController {
    @Autowired
    private TranscationService transcationService;

    @RequestMapping("/test")
    public String test() {
        try {
            transcationService.test();
            return "分布式事务执行成功！";
        } catch (CommonException e) {
            //e.printStackTrace();
            log.info("cdoe:{},错误提示:{}",e.getCode(),e.getMsg());
        }
        return "分布式事务执行失败！";
    }

}
