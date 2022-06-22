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

    @GetMapping("/exportExcel")
    public void exportExcel(){
        exportService.exportExcel();
    }

    @GetMapping("/exportImageZip")
    public void exportImageZip(){
        exportService.exportImageZip();
    }

    @GetMapping("/exportExcelAndImageZip")
    public void exportExcelAndImageZip(){
        exportService.exportExcelAndImageZip();
    }
}
