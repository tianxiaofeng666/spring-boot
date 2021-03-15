package com.example.springbootjob.service.impl;

import com.example.springbootjob.service.QuartzService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QuartzServiceImpl implements QuartzService {

    @Autowired
    private Scheduler scheduler;

    @Override
    public void addJob(String jobClassName, String jobName, String groupName, String cronExpression, String url) {
        try {
            //构建job信息
            JobDetail jobDetail =
                    JobBuilder.newJob((Class<? extends Job>)Class.forName(jobClassName))
                            .withIdentity(jobName, groupName)
                            .usingJobData("url",url)
                            .build();
            //表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            scheduleBuilder.withMisfireHandlingInstructionDoNothing();
            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, groupName)
                    .startNow().withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
            // 启动调度器
            scheduler.start();
            log.info("创建定时任务成功！");
        }catch (Exception e){
            log.error("创建定时任务失败");
        }
    }
}
