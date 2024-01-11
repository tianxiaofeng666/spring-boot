package com.example.demo.controller;

import com.example.demo.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shs-cyhlwzytxf
 */
@RestController
@RequestMapping("/export")
public class ExportController {

    @Autowired
    ExportService exportService;

    /**
     * 导出excel
     */
    @GetMapping("/exportExcel")
    public void exportExcel(){
        exportService.exportExcel();
    }

    /**
     * 导出excel到压缩包,不产生临时路径
     */
    @GetMapping("/exportExcelZip")
    public void exportExcelZip(){
        exportService.exportExcelZip();
    }

    /**
     * 导出学生二维码到excel并分两列展示
     */
    @GetMapping("/exportQrCodeExcel")
    public void exportQrCodeExcel(){
        exportService.exportQrCodeExcel();
    }

    /**
     * 导出学生二维码到excel按班级多sheet并分两列展示
     */
    @GetMapping("/exportQrCodeMultiSheetExcel")
    public void exportQrCodeMultiSheetExcel(){
        exportService.exportQrCodeMultiSheetExcel();
    }

    /**
     * 导出图片到压缩包
     */
    @GetMapping("/exportImageZip")
    public void exportImageZip(){
        exportService.exportImageZip();
    }

    /**
     * 导出图片到压缩包，并分类创建目录
     */
    @GetMapping("/exportImageDirectoryZip")
    public void exportImageDirectoryZip(){
        exportService.exportImageDirectoryZip();
    }

    /**
     * 导出excel和图片到压缩包
     */
    @GetMapping("/exportExcelAndImageZip")
    public void exportExcelAndImageZip(){
        exportService.exportExcelAndImageZip();
    }

    /**
     * 下载模板，省市县级联
     */
    @GetMapping("/downloadTemplate")
    public void downloadTemplate(){
        exportService.downloadTemplate();
    }

    /**
     * 多sheet导出
     */
    @GetMapping("/multiSheetExport")
    public void multiSheetExport(){
        exportService.multiSheetExport();
    }
}
