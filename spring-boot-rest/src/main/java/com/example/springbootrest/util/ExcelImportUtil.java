package com.example.springbootrest.util;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.example.springbootrest.listener.MemberListener;
import com.example.springbootrest.listener.MemberListener11;
import com.example.springbootrest.pojo.Member;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.SheetCollate;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ExcelImportUtil {
    /**
     * 读取某个 sheet 的 Excel
     * 2.0版本前的读取方法
     * @param excel    文件
     * @param member 实体类映射
     * @return Excel 数据 list
     */
    public static List<Member> readExcel(MultipartFile excel, Member member) throws IOException {
        return readExcel(excel, member, 1, 1);
    }

    /**
     * 读取某个 sheet 的 Excel
     * @param excel       文件
     * @param member    实体类映射
     * @param sheetNo     sheet 的序号 从1开始
     * @param headLineNum 表头行数，默认为1
     * @return Excel 数据 list
     */
    public static List<Member> readExcel(MultipartFile excel, Member member, int sheetNo, int headLineNum) throws IOException {
        MemberListener11 listener = new MemberListener11();
        ExcelReader reader = getReader(excel, listener);
        if (reader == null) {
            return null;
        }
        reader.read(new Sheet(sheetNo,headLineNum),member.getClass());
        return listener.getList();
    }

    /**
     * 返回 ExcelReader
     * @param excel 需要解析的 Excel 文件
     * @param listener new MemberListener11()
     * @throws IOException
     */
    private static ExcelReader getReader(MultipartFile excel,MemberListener11 listener) throws IOException {
        String filename = excel.getOriginalFilename();
        if(Objects.nonNull(filename) && (filename.toLowerCase().endsWith(".xls") || filename.toLowerCase().endsWith(".xlsx"))){
            InputStream is = new BufferedInputStream(excel.getInputStream());
            return new ExcelReader(is, null,null, listener, false);
        }else{
            return null;
        }
    }


}
