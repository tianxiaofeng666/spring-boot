package com.example.iybexportplatform.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Person {
    @ExcelProperty("序号")
    private int id;
    @ExcelProperty("姓名*")
    private String userName;
    @ExcelProperty("人员编号")
    private String code;
    @ExcelProperty("部门编号")
    private String depart;

    public Person() {
    }

    public Person(int id, String userName, String code, String depart) {
        this.id = id;
        this.userName = userName;
        this.code = code;
        this.depart = depart;
    }
}
