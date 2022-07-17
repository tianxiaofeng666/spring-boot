package com.example.demo.model.vo;

import lombok.Data;

@Data
public class ExcelCityDataValidationVo {

    public ExcelCityDataValidationVo() {}

    public ExcelCityDataValidationVo(String offset, Integer colNum) {
        this.offset = offset;
        this.colNum = colNum;
    }

    private String offset;
    private Integer colNum;
}
