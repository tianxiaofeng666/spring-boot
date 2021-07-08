package com.example.clouduser.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.clouduser.service.NacosRemote;
import org.springframework.stereotype.Component;

@Component
public class NacosRemoteHystrix implements NacosRemote {
    @Override
    public String queryUserCenter(JSONObject json) {
        return "你访问的接口暂时不可用，请稍后再试！";
    }
}
