package com.example.springbootmybatis.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author code-generator
 * @date 2021-5-19 11:09:12
 */
public class Common {

    public final static Logger logger = LoggerFactory.getLogger(Common.class);

    /**
     * 如果一个对象的字段是空字符串，则变成null
     * 前端传来的空字符串，后端认为是null
     *
     * @param obj
     * @return 返回属性名称
     */
    public static <T> T letFieldNullIfEmpty(T obj) {
        try {
            for (Field f : obj.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.get(obj) != null && StringUtils.isEmpty(f.get(obj))) {
                    f.set(obj, null);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return obj;
    }

    public static void fillDefaultValue(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        // 遍历所有属性
        for (Field field : fields) {
            try {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                if (field.get(obj) == null) {
                    if (field.getType() == String.class) {
                        field.set(obj, "");
                    } else if (field.getType().equals(Integer.class)) {
                        field.set(obj, 0);
                    } else if (field.getType().equals(Long.class)) {
                        field.set(obj, 0L);
                    } else if (field.getType().equals(Float.class)) {
                        field.set(obj, 0f);
                    } else if (field.getType().equals(Double.class)) {
                        field.set(obj, 0d);
                    } else if (field.getType() == BigDecimal.class) {
                        field.set(obj, BigDecimal.ZERO);
                    } else if (field.getType() == Boolean.class) {
                        field.set(obj, false);
                    } else if (field.getType() == Date.class) {
                        field.set(obj, new Date());
                    } else if (field.getType() == LocalDateTime.class) {
                        field.set(obj, LocalDateTime.now());
                    } else if (field.getType() == LocalDate.class) {
                        field.set(obj, LocalDate.now());
                    }
                }

            } catch (Exception e) {
                logger.error("fillDefaultValue", e);
            }
        }

    }

}
