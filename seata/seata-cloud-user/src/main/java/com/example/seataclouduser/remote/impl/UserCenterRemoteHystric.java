package com.example.seataclouduser.remote.impl;

import com.example.seataclouduser.remote.UserCenterRemote;
import org.springframework.stereotype.Component;

@Component
public class UserCenterRemoteHystric implements UserCenterRemote {
    @Override
    public boolean addUser() {
        return false;
    }
}
