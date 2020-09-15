package com.example.zookeeperlock.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.zookeeperlock.service.LockService;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class LockController {

    @Autowired
    private LockService lockService;

    @RequestMapping("/sort")
    public List<Long> sort(){
        List<Long> idList = new ArrayList<>();
        idList.add(4L);
        idList.add(1L);
        idList.add(2L);
        idList.add(3L);
        Collections.sort(idList);
        return idList;
    }

    @RequestMapping("/getNode")
    public String getNode() throws KeeperException, InterruptedException, IOException {
        lockService.connect("localhost:" + "2181");
        //zookeeper的根节点
        String groupPath = "/locks";
        return lockService.join(groupPath);
    }

    @RequestMapping("/lock")
    public boolean lock(@RequestBody JSONObject json) throws IOException, InterruptedException, KeeperException {
        lockService.connect("localhost:" + "2181");

        //zookeeper的根节点
        String groupPath = "/locks";

        String myName = json.getString("myNode");
        boolean isExist = lockService.isExist(myName);
        if(isExist){
            boolean result = lockService.checkState(groupPath,myName);
            if(result){
                return true;
            }else{
                return lockService.listenNode(groupPath,myName);
            }
        }else {
            return false;
        }
    }
}
