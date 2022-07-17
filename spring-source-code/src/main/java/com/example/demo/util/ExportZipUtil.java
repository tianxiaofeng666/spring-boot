package com.example.demo.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.example.demo.config.MinioProperties;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author shs-cyhlwzytxf
 * 导出zip,包含图片和excel
 */
@Slf4j
@Component
public class ExportZipUtil {
    private static MinioClient minioClient;

    @Autowired
    public void setMinioClient(MinioClient minioClient) {
        ExportZipUtil.minioClient = minioClient;
    }

    public static void downloadImage(Map<String, String> imgMap, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/zip");
        response.setHeader("Content-Disposition", "attachment;filename="
                + URLEncoder.encode(System.currentTimeMillis() + ".zip", "UTF-8"));
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        ZipOutputStream zos = new ZipOutputStream(bos);
        byte[] buffer = new byte[1024];
        //  压缩图片信息
        for (Map.Entry<String, String> entry : imgMap.entrySet()) {
            String key  = entry.getKey();
            String value = entry.getValue();
            InputStream is = null;
            try {
                is = minioClient.getObject(MinioProperties.getBucketName(), value);
                if (is == null) {
                    continue;
                }
                String suffix = value.substring(value.lastIndexOf("."));
                zos.putNextEntry(new ZipEntry(key + suffix));
                int len;
                while ((len = is.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                is.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }
        zos.close();
        bos.close();
    }

    public static void downLoadExcelAndImage(List<?> list, String title, String sheetName, Class<?> pojoClass,
                                             String fileName, Map<String, String> headImgMap, HttpServletResponse response) throws IOException{
        ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        String excelPath = ExcelUtils.downLoadExcelByPath(System.getProperty("java.io.tmpdir") + fileName + ".xlsx",
                workbook);
        exportExcelAndImage(headImgMap, excelPath, response);
    }

    /**
     * 下载Excel和头像信息
     * @param headImgMap  头像信息
     * @param excelPath   excel路径
     * @return
     * @throws IOException
     */
    private static void exportExcelAndImage(Map<String, String> headImgMap, String excelPath, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/zip");
        response.setHeader("Content-Disposition", "attachment;filename="
                + URLEncoder.encode(System.currentTimeMillis() + ".zip", "UTF-8"));
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        ZipOutputStream zos = new ZipOutputStream(bos);
        byte[] buffer = new byte[1024];
        //  压缩头像图片信息
        for (Map.Entry<String, String> entry : headImgMap.entrySet()) {
            String key  = entry.getKey();
            String value = entry.getValue();
            InputStream is = null;
            try {
                is = minioClient.getObject(MinioProperties.getBucketName(), value);
                if (is == null) {
                    continue;
                }
                String suffix = value.substring(value.lastIndexOf("."));
                zos.putNextEntry(new ZipEntry(key + suffix));
                int len;
                while ((len = is.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                is.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }
        //  压缩excel
        FileInputStream fis = null;
        try {
            File excelFile = new File(excelPath);
            fis = new FileInputStream(excelFile);
            zos.putNextEntry(new ZipEntry(excelFile.getName()));
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            zos.closeEntry();
            fis.close();
            Path excelFilePath = excelFile.toPath();
            Files.delete(excelFilePath);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        zos.close();
        bos.close();
    }
}
