package com.example.demo.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Excel导入导出工具类
 *
 * @author shs-cyhlwzytxf
 * @date 2021-09-02
 */
@Slf4j
public class ExcelUtils {

    /**
     * 基于pojo类注解excel导出
     *
     * @param list      数据列表
     * @param title     表格内数据标题
     * @param sheetName sheet名称
     * @param pojoClass pojo类型
     * @param fileName  导出时的excel名称
     * @param response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) throws IOException {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName, ExcelType.XSSF));
    }

    /**
     * 自定义excel导出
     *
     * @param title     标题
     * @param sheetName sheet名称
     * @param excelList 表头
     * @param list      列表数据
     * @param fileName  文件名称
     * @param response
     * @throws IOException
     */
    public static void exportExcel(String title, String sheetName, List<ExcelExportEntity> excelList, List<Map<String, Object>> list, String fileName, HttpServletResponse response) throws IOException {
        ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
        //设置导出格式
        exportParams.setStyle(CustomExcelExportStyler.class);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, excelList, list);
        downLoadExcel(fileName, response, workbook);
    }

    /**
     * 基于pojo类注解excel导出，带下拉框
     *
     * @param list      数据列表
     * @param title     表格内数据标题
     * @param sheetName sheet名称
     * @param pojoClass pojo类型
     * @param fileName  导出时的excel名称
     * @param response
     * @param selectMap 下拉框
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response, Map<Integer, String[]> selectMap) throws IOException {
        ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
        exportParams.setStyle(CustomExcelExportStyler.class);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        for (Map.Entry<Integer, String[]> entry : selectMap.entrySet()) {
            int column = entry.getKey();
            String[] strings = entry.getValue();
            ExcelSelectListUtil.selectList(workbook, column, column, strings);
        }
        downLoadExcel(fileName, response, workbook);
    }

    /**
     * excel 导出
     *
     * @param list         数据列表
     * @param pojoClass    pojo类型
     * @param fileName     导出时的excel名称
     * @param response
     * @param exportParams 导出参数（标题、sheet名称、是否创建表头，表格类型）
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) throws IOException {
        //把数据添加到excel表格中
        exportParams.setStyle(CustomExcelExportStyler.class);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        downLoadExcel(fileName, response, workbook);
    }

    /**
     * excel下载
     *
     * @param fileName 下载时的文件名称
     * @param response
     * @param workbook excel数据
     */
    public static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) throws IOException {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * excel下载
     *
     * @param path 下载时的文件路径
     * @param path
     * @param workbook excel数据
     */
    public static String downLoadExcelByPath(String path, Workbook workbook) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(path);
            fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            return file.getPath();
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        } finally {
            if (fileOutputStream != null) { fileOutputStream.close(); }
            if (workbook != null) { workbook.close(); }
        }
    }

    /**
     * excel 导入
     *
     * @param file      excel文件
     * @param pojoClass pojo类型
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Class<T> pojoClass) throws IOException {
        return importExcel(file, 0, 1, pojoClass);
    }

    /**
     * excel 导入
     *
     * @param filePath   excel文件路径
     * @param titleRows  表格内数据标题行
     * @param headerRows 表头行
     * @param pojoClass  pojo类型
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) throws IOException {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        params.setNeedSave(true);
        params.setSaveUrl("/excel/");
        try {
            return ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new IOException("模板不能为空");
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * excel 导入
     *
     * @param file       上传的文件
     * @param titleRows  表格内数据标题行
     * @param headerRows 表头行
     * @param pojoClass  pojo类型
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) throws IOException {
        if (file == null) {
            return null;
        }
        try {
            return importExcel(file.getInputStream(), titleRows, headerRows, pojoClass);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * excel 导入
     *
     * @param inputStream 上传的文件流
     * @param titleRows  表格内数据标题行
     * @param headerRows 表头行
     * @param pojoClass  pojo类型
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcelByStream(InputStream inputStream, Integer titleRows, Integer headerRows, Class<T> pojoClass)
            throws IOException {
        if (inputStream == null) { return null; }

        try {
            return importExcel(inputStream, titleRows, headerRows, pojoClass);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * 功能描述：根据接收的Excel文件来导入多个sheet,根据索引可返回一个集合
     * @param file   文件
     * @param sheetIndex  导入sheet索引
     * @param titleRows  表标题的行数
     * @param headerRows 表头行数
     * @param pojoClass  Excel实体类
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, int sheetIndex, Integer titleRows, Integer headerRows, Class<T> pojoClass) throws IOException{
        // 根据file得到Workbook,主要是要根据这个对象获取,传过来的excel有几个sheet页
        ImportParams params = new ImportParams();
        // 第几个sheet页
        params.setStartSheetIndex(sheetIndex);
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new IOException("模板不能为空");
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
        return list;
    }
    /**
     * excel 导入
     *
     * @param inputStream 文件输入流
     * @param titleRows   表格内数据标题行
     * @param headerRows  表头行
     * @param pojoClass   pojo类型
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(InputStream inputStream, Integer titleRows, Integer headerRows, Class<T> pojoClass) throws IOException {
        if (inputStream == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
//        params.setSaveUrl("/excel/");
        params.setNeedSave(false);
        try {
            return ExcelImportUtil.importExcel(inputStream, pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new IOException("excel文件不能为空");
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * excel 导入
     *
     * @param inputStream 文件输入流
     * @param titleRows   表格内数据标题行
     * @param headerRows  表头行
     * @param pojoClass   pojo类型
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(InputStream inputStream, Integer titleRows, Integer headerRows, Class<T> pojoClass, Integer keyIndex)
            throws IOException {
        if (inputStream == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        params.setNeedSave(false);
        params.setKeyIndex(keyIndex);
        try {
            return ExcelImportUtil.importExcel(inputStream, pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new IOException("excel文件不能为空");
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * excel导出时
     * 返回头设置下载，并设置文件名
     * 注意：以下设置编码格式是为了ie，当前项目是ie下使用的。360或者谷歌可能会文件名会乱码。根据自己需要调整编码。或者不用设置这么多，直接outStream输出得了。
     *
     * @param response
     * @param workbook
     * @param fileName
     * @throws Exception
     */
    public static void setExportExcelFormat(HttpServletResponse response, Workbook workbook, String fileName) throws Exception {

        try {
            response.reset();
            //下载
            response.setContentType("application/x-msdownload");
            fileName = fileName + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try (ServletOutputStream outStream = response.getOutputStream()) {
                workbook.write(outStream);
            }
        }
    }
}
