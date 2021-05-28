/** 以下代码由代码生成器自动生成，如无必要，请勿修改！2021-5-19 11:09:12 **/
package com.example.plus.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.plus.bean.PageQueryModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.StringUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author code-generator
 * @date 2021-5-19 11:09:12
 */
 public class QueryWrapperUtil {
       static public <T extends PageQueryModel, K> QueryWrapper<K> createQueryWrapper(T queryModel) {
           QueryWrapper<K> queryWrapper = new QueryWrapper<>();
           Field[] fields = queryModel.getClass().getDeclaredFields();
           // 遍历所有属性
           for (Field field : fields) {
               try {
                   field.setAccessible(true);
                   ApiModelProperty apiModelPropertyAnnotation = field.getAnnotation(ApiModelProperty.class);
                   //如果该属性没有检索条件，直接跳过
                   if (Modifier.isStatic(field.getModifiers())
                           || apiModelPropertyAnnotation == null
                           || field.get(queryModel) == null
                           || StringUtils.isEmpty(field.get(queryModel))) {
                       continue;
                   }
                    //检索条件中日期处理
                   boolean isTimeType = (field.getType() == LocalTime.class || field.getType() == LocalDate.class || field.getType() == LocalDateTime.class) && field.get(queryModel) != null;
                   if (isTimeType) {
                       String notes = apiModelPropertyAnnotation.notes();
                       if ("TimeFrom".equalsIgnoreCase(notes)) {
                           //大于等于
                           queryWrapper.ge(apiModelPropertyAnnotation.name(), field.get(queryModel));
                           continue;
                       } else if ("TimeTo".equalsIgnoreCase(notes)) {
                           //小于等于
                           queryWrapper.le(apiModelPropertyAnnotation.name(), field.get(queryModel));
                           continue;
                       }
                   }
                   if(apiModelPropertyAnnotation.required()){
                       queryWrapper.like(apiModelPropertyAnnotation.name(), field.get(queryModel));
                   }else{
                       queryWrapper.eq(apiModelPropertyAnnotation.name(), field.get(queryModel));
                   }
               } catch (Exception e) {

               }
           }
           queryWrapper.orderByAsc(queryModel.getOrderByAsc() != null && queryModel.getOrderByAsc().length > 0, queryModel.getOrderByAsc());
           queryWrapper.orderByDesc(queryModel.getOrderByDesc() != null && queryModel.getOrderByDesc().length > 0, queryModel.getOrderByDesc());
           return queryWrapper;
       }
  }