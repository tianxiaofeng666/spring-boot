package com.example.springbootschedule.aspect;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DisSchedule {

    /**
     * 定时调度任务的名称(默认是方法名)
     */
    String name() default "";
}
