package com.example.springbootjob.job;

import com.example.springbootjob.mapper.UserDao;
import com.example.springbootjob.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class UserJob extends QuartzJobBean {

    @Resource
    private UserDao userDao;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(date);
        log.info("定时任务开始执行****当前时间：{}",currentTime);

        User user = new User();
        user.setName("zyt");
        user.setGender("M");
        user.setAge("30");
        user.setMobile("17521048888");
        user.setGmtCreated(currentTime);
        log.info("用户信息：{}",user.toString());
        userDao.addUser(user);
    }
}
