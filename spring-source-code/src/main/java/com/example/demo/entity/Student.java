package com.example.demo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shs-cyhlwzytxf
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Excel(name = "姓名",orderNum = "0")
    private String name;
    @Excel(name = "性别",orderNum = "1")
    private String gender;
    @Excel(name = "证件号",orderNum = "2")
    private String idCard;
}
