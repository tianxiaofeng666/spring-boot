package com.example.demo.service;

import com.example.demo.config.MinioProperties;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author shs-cyhlwzytxf
 */
@Slf4j
@Service
public class ExcelService {

    @Autowired
    private MinioClient minioClient;

    public void downloadImage(Map<String, String> imgMap, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/zip");
        response.setHeader("Content-Disposition", "attachment;filename="
                + URLEncoder.encode(String.valueOf(System.currentTimeMillis()) + ".zip", "UTF-8"));

        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        ZipOutputStream zos = new ZipOutputStream(bos);
        byte[] buffer = new byte[1024];

        //  压缩图片信息
        if (imgMap != null && imgMap.size() > 0) {
            for (String key : imgMap.keySet()) {
                String[] imageUrls = imgMap.get(key).split(",");

                for (int j = 0; j < imageUrls.length; j++) {
                    InputStream is = null;
                    try {
                        is = minioClient.getObject(MinioProperties.getBucketName(), imageUrls[j]);
                        if (is == null) {
                            continue;
                        }

                        String fileName = imageUrls[j].substring(imageUrls[j].lastIndexOf("/") + 1, imageUrls[j].length());
                        String tail = fileName.split("[.]")[1];
                        zos.putNextEntry(new ZipEntry(key + "." + tail));

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
            }
        }
        zos.close();
    }
}
