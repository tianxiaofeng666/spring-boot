package com.example.springbootjob.service;

public interface QuartzService {

    public void addJob(String jobClassName, String jobName, String groupName, String cronExpression, String url);
}
