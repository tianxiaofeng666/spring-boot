package com.example.demo.controller;

import com.example.demo.util.CommonFileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @author shs-cyhlwzytxf
 */
@RestController
@RequestMapping("/minio")
public class MinioController {

    @PostMapping("/upload")
    public String uploadPic(@RequestParam(name = "file") MultipartFile file) {
        return CommonFileUtils.uploadFileToMinio(file);
    }

    @GetMapping("/getFileSource")
    public void getFileSource(@RequestParam(value = "fileName") String fileName, HttpServletResponse response) {
        CommonFileUtils.getFileSource(fileName,response);
    }
}
