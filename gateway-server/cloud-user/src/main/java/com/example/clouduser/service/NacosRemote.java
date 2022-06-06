package com.example.clouduser.service;

import com.alibaba.fastjson.JSONObject;
import com.example.clouduser.config.FeignConfiguration;
import com.example.clouduser.service.impl.NacosRemoteHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "user-center", configuration = FeignConfiguration.class, fallback = NacosRemoteHystrix.class)
public interface NacosRemote {

    @PostMapping("/userCenter/api/user/queryUserCenter")
    String queryUserCenter(JSONObject json);

    @GetMapping("/userCenter/api/user/sendData")
    String sendData(@RequestParam("no") String no);
}
