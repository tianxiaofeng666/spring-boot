package com.example.demo.constant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author code-generator
 * @date 2021-3-18 14:32:33
 */
public class DateTimeConstant {
    private DateTimeConstant(){

    }

    public static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.of(1900, 1, 1, 0, 0);

    public static final DateTimeFormatter DEFAULT_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter DEFAULT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final DateTimeFormatter DEFAULT_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static final DateTimeFormatter DEFAULT_TIME_STAMP_NANOSECOND_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssnnnnnnnnn");

    public static final DateTimeFormatter EXPORT_FILENAME_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static final DateTimeFormatter EXCEL_DATE_FORMAT = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
}