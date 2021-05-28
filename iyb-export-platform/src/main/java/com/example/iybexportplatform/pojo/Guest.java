package com.example.iybexportplatform.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Guest {

    @ExcelProperty("序号")
    private int id;
    @ExcelProperty("姓名*")
    private String guestName;
    @ExcelProperty("手机号码*")
    private String guestMobile;
    @ExcelProperty("证件号码")
    private String guestIdcard;

    public Guest(int id, String guestName, String guestMobile, String guestIdcard) {
        this.id = id;
        this.guestName = guestName;
        this.guestMobile = guestMobile;
        this.guestIdcard = guestIdcard;
    }

    public Guest() {
    }
}
