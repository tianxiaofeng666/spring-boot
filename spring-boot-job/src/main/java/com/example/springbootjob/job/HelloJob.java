package com.example.springbootjob.job;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class HelloJob extends QuartzJobBean {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String url = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("url");
        JSONObject aa = new JSONObject();
        aa.put("param","txf");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("charset","UTF-8");
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(aa, headers);
        //ResponseEntity<String> result = restTemplate.postForEntity(url, httpEntity, String.class);
        log.info("定时任务正在执行----{},返回结果：{}",url,"系统繁忙！");
    }
}
