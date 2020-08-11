package com.example.springbootrest.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Member {
    @ExcelProperty("序号")
    private int id;
    @ExcelProperty("姓名*")
    private String name;
    @ExcelProperty("手机号码*")
    private String mobile;

    public Member(int id, String name, String mobile) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
    }

    public Member() {
    }
}
