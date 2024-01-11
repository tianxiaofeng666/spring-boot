package com.example.demo.model.response;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import lombok.Data;

import java.io.InputStream;

/**
 * @author pengwentao
 */
@Data
@ContentRowHeight(180)
@ColumnWidth(35)
public class ExportStudentCodeResp {


    @ExcelProperty(value = "第一列")
    private InputStream inputStream1;


    @ExcelProperty(value = "第二列")
    private InputStream inputStream2;
}
