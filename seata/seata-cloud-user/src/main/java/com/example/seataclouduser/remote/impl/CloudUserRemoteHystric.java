package com.example.seataclouduser.remote.impl;

import com.example.seataclouduser.remote.CloudUserRemote;
import org.springframework.stereotype.Component;

@Component
public class CloudUserRemoteHystric implements CloudUserRemote {
    @Override
    public boolean addUser() {
        return false;
    }
}
