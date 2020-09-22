package com.example.springbootschedule.task;

import com.example.springbootschedule.aspect.DisSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class CloudTask {

    private static final Logger log = LoggerFactory.getLogger(CloudTask.class);

    @Scheduled(cron = "0 0/2 * * * ?")
    @DisSchedule(name = "aa")
    public void aa() {
        log.info("定时任务开始执行111。。。");

    }
}
