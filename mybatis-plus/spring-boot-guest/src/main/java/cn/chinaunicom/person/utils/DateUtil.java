package cn.chinaunicom.person.utils;

import cn.hutool.core.date.format.FastDateFormat;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期类型工具类
 * @author dengxz16
 */
public class DateUtil
{
    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /** 时间格式(yyyy-MM-dd) */
    public final static String DATE_PATTERN = "yyyy-MM-dd";

    /** 时间格式(yyyy-MM-dd HH:mm:ss) */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /** 时间格式(yyyy-MM-dd'T'HH:mm:ss.SSS'Z') */
    public final static String DATE_TIME_PATTERN_T = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * 字符串转换成日期
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtil.DATE_TIME_PATTERN
     * @return
     */
    public static LocalDateTime stringLocalDateTime(String strDate, String pattern)
    {
        if (StringUtils.isBlank(strDate))
        {
            return null;
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(strDate,fmt);
    }

    /**
     * Date转换为LocalDateTime
     * @param date
     */
    public static LocalDateTime dateLocalDateTime(Date date)
    {
        if( date == null)
        {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * LocalDateTime转成带时区的字符串
     * 格式如：2020-08-05T13:31:36 + 08:00
     * @param date
     */
    public static String dateTimeZoneTime(LocalDateTime date)
    {
        String zoneTime = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss ZZ").format(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()));
        return zoneTime.replaceAll(" ", "");
    }

    /**
     * LocalDateTime转换为String
     * @param localDateTime
     * @param pattern
     */
    public static String localDateTimeString(LocalDateTime localDateTime, String pattern)
    {
        if(localDateTime == null)
        {
            return null;
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(fmt);
    }

    /**
     * 字符串转换成日期
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtil.DATE_TIME_PATTERN
     * @return
     */
    public static LocalDateTime string2LocalDateTime(String strDate, String pattern) {
        if (StringUtils.isBlank(strDate)){
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(strDate,fmt);
    }

    /**
     * 判断两个时间段是否重复,重复返回true
     * @param start1
     * @param end1
     * @param start2
     * @param end2
     * @return
     */
    public static boolean isOverlapping(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2)
    {
        return (start2.isAfter(start1) && start2.isBefore(end1))
                || (end2.isAfter(start1) && end2.isBefore(end1))
                || (!start2.isAfter(start1) && !end2.isBefore(end1));
    }

    /**
     * 计算两个时间点的时间差，单位：分钟
     * @param start
     * @param end
     * @return
     */
    public static Long calculateTimeDiffByMinute(LocalDateTime start,LocalDateTime end) {
        return Duration.between(start,end).toMinutes();
    }
}
