package com.example.sentinelflow.service;

import com.alibaba.fastjson.JSONObject;
import com.example.sentinelflow.service.impl.NacosRemoteHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "sentinel-nacos-consumer", url = "http://localhost:8889/", fallback = NacosRemoteHystrix.class)
public interface NacosRemote {

    @PostMapping("/testNacos")
    public String testNacos(JSONObject json);
}
