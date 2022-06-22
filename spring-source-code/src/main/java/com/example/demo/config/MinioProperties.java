package com.example.demo.config;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author shs-cyhlwzytxf
 * minio搭建：http://t.zoukankan.com/FZlion-p-14380552.html
 *           https://blog.csdn.net/weixin_43317914/article/details/122394554
 */
@Component
public class MinioProperties {
    public static String url;
    public static String accessKey;
    public static String secretKey;
    public static String bucketName;
    public static String uploadMaxFileSize;

    public MinioProperties() {
    }

    public static String getUrl() {
        return url;
    }

    @Value("${minio.url}")
    public void setUrl(String url) {
        MinioProperties.url = url;
    }

    public static String getAccessKey() {
        return accessKey;
    }

    @Value("${minio.access-key}")
    public void setAccessKey(String accessKey) {
        MinioProperties.accessKey = accessKey;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    @Value("${minio.secret-key}")
    public void setSecretKey(String secretKey) {
        MinioProperties.secretKey = secretKey;
    }

    public static String getBucketName() {
        return bucketName;
    }

    @Value("${minio.defaule_bucket_name}")
    public void setBucketName(String bucketName) {
        MinioProperties.bucketName = bucketName;
    }

    public static String getUploadMaxFileSize() {
        return uploadMaxFileSize;
    }

    @Value("${minio.upload_max_filesize}")
    public void setUploadMaxFileSize(String uploadMaxFileSize) {
        MinioProperties.uploadMaxFileSize = uploadMaxFileSize;
    }

    @Bean
    public MinioClient getMinioClient() throws InvalidPortException, InvalidEndpointException {
        MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
        return minioClient;
    }
}
