package com.example.seataclouduser.remote;

import com.example.seataclouduser.remote.impl.UserCenterRemoteHystric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "user-center", url = "http://localhost:3333/", fallback = UserCenterRemoteHystric.class)
public interface UserCenterRemote {
    @RequestMapping("/addUser")
    public boolean addUser();
}
