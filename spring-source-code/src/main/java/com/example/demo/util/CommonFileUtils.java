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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
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

    /**
     * 上传文件（图片、word、excel、pdf）
     * @param file
     * @return
     */
    public static String uploadFileToMinio(MultipartFile file) {
        if (file.isEmpty()) {
            log.info("上传文件为空");
            throw new BusinessException(500,"上传文件为空");
        }
        if (file.getSize() > Long.parseLong(MinioProperties.getUploadMaxFileSize())) {
            log.info("上传文件过大");
            throw new BusinessException(500,"上传文件过大");
        }
        try(InputStream inputStream = file.getInputStream()) {
            String originalFilename = file.getOriginalFilename();
            String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = LocalDate.now() + "/" + System.currentTimeMillis() + RegexUtil.getRandomCode(3) + suffixName;
            log.info("fileName:" + fileName);
            boolean bucketExists = minioClient.bucketExists(MinioProperties.getBucketName());
            if (!bucketExists) {
                minioClient.makeBucket(MinioProperties.getBucketName());
            }
            minioClient.putObject(MinioProperties.getBucketName(), fileName, inputStream,file.getContentType());
            return fileName;
        } catch (Exception e) {
            log.info("minio上传文件失败，请检查配置信息" + e.getMessage());
            throw new BusinessException(500,"minio上传文件失败，请检查配置信息");
        }
    }

    /**
     * 下载图片
     * @param fileName
     * @param response
     */
    public static void getFileSource(String fileName, HttpServletResponse response) {
        log.info("文件名：{}",fileName);
        InputStream inputStream = null;
        try(OutputStream out = response.getOutputStream()) {
            if (StringUtils.isEmpty(fileName)) {
                response.setStatus(404);
                return;
            }
            inputStream = minioClient.getObject(MinioProperties.getBucketName(), fileName);
            byte[] bytes = IOUtils.toByteArray(inputStream);
            response.setContentType("image/jpeg");
            out.write(bytes);
            out.flush();
        } catch (Exception e) {
            log.info("未找到图片：{}",fileName);
            response.setStatus(404);
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 上传图片
     * @param file
     * @return
     */
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
            log.info("fileName:" + fileName);
            boolean bucketExists = minioClient.bucketExists(MinioProperties.getBucketName());
            if (!bucketExists) {
                minioClient.makeBucket(MinioProperties.getBucketName());
            }
            minioClient.putObject(MinioProperties.getBucketName(), fileName, inputStream,file.getContentType());
            return fileName;
        } catch (Exception e) {
            log.info("minio上传文件失败，请检查配置信息" + e.getMessage());
            throw new BusinessException(500,"minio上传文件失败，请检查配置信息");
        }
    }

    /**
     * 获取文件流
     *
     * @param bucketName bucket名称
     * @param fileName 文件名称
     * @return 二进制流
     */
    public static InputStream getObject(String bucketName, String fileName)
    {
        InputStream ins = null;
        try {
            ins = minioClient.getObject(bucketName, fileName);
        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ins;
    }

    /**
     * 下载文件（图片、word、excel、pdf）
     * https://blog.csdn.net/xiaomogg/article/details/130829971
     */
    public static void downloadFile(String fileUrl, HttpServletResponse response) {
        InputStream inputStream = null;
        try(OutputStream outputStream = response.getOutputStream()) {
            if (StringUtils.isBlank(fileUrl)) {
                response.setHeader("Content-type", "text/html;charset=UTF-8");
                String data = "文件下载失败";
                outputStream.write(data.getBytes("UTF-8"));
                return;
            }
            inputStream = minioClient.getObject(MinioProperties.getBucketName(), fileUrl);
            byte[] buf = new byte[1024];
            int length = 0;
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    URLEncoder.encode(fileUrl, "UTF-8"));
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            // 输出文件
            while ((length = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, length);
            }
        } catch (Exception ex) {
            log.error("文件下载失败");
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
