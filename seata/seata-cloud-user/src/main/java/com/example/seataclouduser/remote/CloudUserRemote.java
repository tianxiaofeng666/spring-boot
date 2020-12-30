package com.example.seataclouduser.remote;

import com.example.seataclouduser.remote.impl.CloudUserRemoteHystric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "cloud-user", url = "http://localhost:4444/", fallback = CloudUserRemoteHystric.class)
public interface CloudUserRemote {
    @RequestMapping("/addUser")
    public boolean addUser();
}
