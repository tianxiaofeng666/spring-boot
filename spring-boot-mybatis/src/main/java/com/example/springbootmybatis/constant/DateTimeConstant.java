package com.example.springbootmybatis.constant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author code-generator
 * @date 2021-5-19 11:09:12
 */
 public class DateTimeConstant {

     public final static LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.of(1900, 1, 1, 0, 0);

     public final static DateTimeFormatter DEFAULT_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

     public static final DateTimeFormatter DEFAULT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

     public static final DateTimeFormatter DEFAULT_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
 }