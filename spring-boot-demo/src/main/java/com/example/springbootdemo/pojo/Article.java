package com.example.springbootdemo.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Article {
    @ExcelProperty("文章标题")
    private String title;
    @ExcelProperty("文章地址")
    private String contentUrl;
    @ExcelProperty("文章简介")
    private String content;
    @ExcelProperty("博主头像")
    private String photoUrl;
    @ExcelProperty("博主名称")
    private String name;
    @ExcelProperty("创建日期")
    private String createTime;
}
