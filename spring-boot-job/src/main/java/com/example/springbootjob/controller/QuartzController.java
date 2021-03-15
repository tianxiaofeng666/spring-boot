package com.example.springbootjob.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootjob.service.QuartzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class QuartzController {

    @Autowired
    private QuartzService quartzService;

    @RequestMapping("/addJob.json")
    public String addJob(@RequestBody JSONObject json){
        String jobClassName = json.getString("jobClassName");
        String jobName = json.getString("jobName");
        String jobGroupName = json.getString("jobGroupName");
        String cronExpression = json.getString("cronExpression");
        String url = json.getString("url");
        quartzService.addJob(jobClassName,jobName,jobGroupName,cronExpression,url);
        return "定时任务创建成功";
    }

}
