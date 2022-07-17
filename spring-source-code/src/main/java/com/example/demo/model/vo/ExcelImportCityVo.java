package com.example.demo.model.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author RX
 */
@Data
public class ExcelImportCityVo {

    @Excel(name = "省", width = 20)
    private String province;

    @Excel(name = "市", width = 20)
    private String city;

    @Excel(name = "县", width = 30)
    private String district;

}
