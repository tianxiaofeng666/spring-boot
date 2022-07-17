package com.example.demo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import com.example.demo.model.vo.ExcelImportCityVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author RX
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @Excel(name = "姓名", orderNum = "0")
    private String name;

    @Excel(name = "性别", orderNum = "1")
    private String genderName;

    @Excel(name = "省", orderNum = "2", groupName = "籍贯")
    private String province;

    @Excel(name = "市", orderNum = "3", groupName = "籍贯")
    private String city;

    @Excel(name = "县", orderNum = "4", groupName = "籍贯")
    private String district;

    /*@ExcelCollection(name = "籍贯*", orderNum = "2")
    private List<ExcelImportCityVo> nativePlaceName;*/
}
