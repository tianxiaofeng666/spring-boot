package com.example.springbootschedule.aspect;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootschedule.task.CloudTask;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class DisScheduleAspect {

    private static final Logger log = LoggerFactory.getLogger(DisScheduleAspect.class);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 以自定义DisSchedule 注解为切点
     */
    @Pointcut("@annotation(com.example.springbootschedule.aspect.DisSchedule)")
    public void disScheduleAnnotation(){}

    /**
     * 环绕
     */
    @Around("disScheduleAnnotation() && @annotation(disSchedule)")
    public Object doAroud(ProceedingJoinPoint joinPoint, DisSchedule disSchedule) throws Throwable{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String currentDate = sdf.format(new Date());
        log.info("===start===");

        //获取name
        String name = disSchedule.name(); //该name是注解里面的
        if(StringUtils.isNotBlank(name)){
            //方法名
            Signature signature = joinPoint.getSignature();
            name = signature.getName();
        }

        String key = currentDate + name;
        String value = name;
        long time = 60L;
        log.info("key:" + key);

        JSONObject obj = new JSONObject();
        obj.put("key",key);
        obj.put("value",value);
        obj.put("time",time);

        //获取分布式锁
        String url = "http://127.0.0.1:8080/getLock";
        Boolean result = restTemplate.postForObject(url, obj, Boolean.class);
        log.info("结果：" + result);

        //集群中获取到锁的服务执行定时任务，其他服务不执行
        if(!result){
            log.info("服务没有获取到锁，不执行定时任务。。");
            return null;
        }
        log.info("获取到锁啦！！！！");
        //执行正常的逻辑
        return joinPoint.proceed();

    }

}
