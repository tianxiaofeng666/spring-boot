package com.example.demo.util;

import com.example.demo.config.MinioProperties;
import com.example.demo.exception.BusinessException;
import io.minio.MinioClient;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;

/**
 * @author shs-cyhlwzytxf
 */
@Component
public class CommonFileUtils {
    private static final Logger log = LoggerFactory.getLogger(CommonFileUtils.class);
    private static MinioClient minioClient;
    private static final String IMAGE_TYPE = "image/jpeg";
    private static final String PICTURE_SUFFIX = "^.+\\.(jpg|png|jpeg|bmp|JPG|PNG|BMP)$";

    private static final String QRCODE_SAVE_PATH = "qrCode/";

    public CommonFileUtils() {
    }

    @Autowired
    public void setMinioClient(MinioClient minioClient) {
        CommonFileUtils.minioClient = minioClient;
    }

    public static String uploadFileToMinio(MultipartFile file) {
        if (file.isEmpty()) {
            log.info("上传文件为空");
            throw new BusinessException(500,"上传文件为空");
        }
        if (file.getSize() > Long.parseLong(MinioProperties.getUploadMaxFileSize())) {
            log.info("上传文件过大");
            throw new BusinessException(500,"上传文件过大");
        }
        log.info("fileName:" + file.getOriginalFilename());
        if (!file.getOriginalFilename().matches("^.+\\.(jpg|png|jpeg|bmp|JPG|PNG|BMP)$")) {
            log.info("文件格式错误");
            throw new BusinessException(500,"文件格式错误");
        }
        try(InputStream inputStream = file.getInputStream()) {
            String fileName = LocalDate.now() + "/" + System.currentTimeMillis() + RegexUtil.getRandomCode(3) + ".jpg";
            log.info("minioClient:" + minioClient);
            log.info("fileName:" + fileName);
            boolean bucketExists = minioClient.bucketExists(MinioProperties.getBucketName());
            log.info("bucketExists:" + bucketExists);
            if (!bucketExists) {
                minioClient.makeBucket(MinioProperties.getBucketName());
            }
            minioClient.putObject(MinioProperties.getBucketName(), fileName, inputStream, "image/jpeg");
            return fileName;
        } catch (Exception e) {
            log.info("minio上传文件失败，请检查配置信息" + e.getMessage());
            throw new BusinessException(500,"minio上传文件失败，请检查配置信息");
        }
    }

    public static void getFileSource(String fileName, HttpServletResponse response) {
        log.info("文件名：{}",fileName);
        try {
            if (StringUtils.isEmpty(fileName)) {
                response.setStatus(404);
                return;
            }
            InputStream inputStream = minioClient.getObject(MinioProperties.getBucketName(), fileName);
            byte[] bytes = IOUtils.toByteArray(inputStream);
            response.setContentType("image/jpeg");
            OutputStream out = response.getOutputStream();
            out.write(bytes);
            out.flush();
            out.close();
        } catch (Exception e) {
            log.info("未找到图片：{}",fileName);
            response.setStatus(404);
        }
    }

    public static String uploadQrCodeToMinio(MultipartFile file){
        if (file.isEmpty()) {
            log.info("上传文件为空");
            throw new BusinessException(500,"上传文件为空");
        }
        if (file.getSize() > Long.parseLong(MinioProperties.getUploadMaxFileSize())) {
            log.info("上传文件过大");
            throw new BusinessException(500,"上传文件过大");
        }
        log.info("fileName:" + file.getOriginalFilename());
        if (!file.getOriginalFilename().matches("^.+\\.(jpg|png|jpeg|bmp|JPG|PNG|BMP)$")) {
            log.info("文件格式错误");
            throw new BusinessException(500,"文件格式错误");
        }
        try(InputStream inputStream = file.getInputStream()){
            String fileName = QRCODE_SAVE_PATH +  file.getOriginalFilename();
            log.info("minioClient:" + minioClient);
            log.info("fileName:" + fileName);
            boolean bucketExists = minioClient.bucketExists(MinioProperties.getBucketName());
            log.info("bucketExists:" + bucketExists);
            if (!bucketExists) {
                minioClient.makeBucket(MinioProperties.getBucketName());
            }
            minioClient.putObject(MinioProperties.getBucketName(), fileName, inputStream, "image/jpeg");
            return fileName;
        } catch (Exception e) {
            log.info("minio上传文件失败，请检查配置信息" + e.getMessage());
            throw new BusinessException(500,"minio上传文件失败，请检查配置信息");
        }
    }
}
