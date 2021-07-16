package com.example.clouduser.service;

import com.alibaba.fastjson.JSONObject;
import com.example.clouduser.service.impl.NacosRemoteHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-center", fallback = NacosRemoteHystrix.class)
@Component
public interface NacosRemote {

    @PostMapping("/userCenter/api/user/queryUserCenter")
    public String queryUserCenter(JSONObject json);
}
