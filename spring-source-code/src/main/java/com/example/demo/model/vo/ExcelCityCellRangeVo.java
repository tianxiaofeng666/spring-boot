package com.example.demo.model.vo;

import lombok.Data;

/**
 * @author shs-cyhlwzypf
 * 设置联动的行和列
 * @date 2022-01-29
 */
@Data
public class ExcelCityCellRangeVo {

    public ExcelCityCellRangeVo() {}

    public ExcelCityCellRangeVo(int firstRow, int lastRow, int firstCol, int lastCol) {
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.firstCol = firstCol;
        this.lastCol = lastCol;
    }

    private int firstRow;
    private int lastRow;
    private int firstCol;
    private int lastCol;

}
