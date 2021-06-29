package com.example.springbootrest.controller;

import com.example.springbootrest.util.IOUtils;
import com.example.springbootrest.util.RegexUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDate;

@RestController
@Slf4j
@RequestMapping("/api/minio")
public class MinioController {

    @Value("${file.docBase}")
    private String docBase;

    @Value("${file.path}")
    private String filePath;

    /**
     * 通过 I/O流 上传文件，图片（jpg/png）、excel、doc 、pdf等
     * @param file
     * @return
     */
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file){
        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = file.getInputStream();
            //String uploadPath = "F:/photo";
            String originalFileName = file.getOriginalFilename();
            String suffixName = originalFileName.substring(originalFileName.lastIndexOf("."));
            //用时间戳+随机数作为文件名，以保证文件名唯一
            String fileName = LocalDate.now() +"/" + System.currentTimeMillis() + RegexUtil.getRandomCode(3) + suffixName;
            //创建文件路径
            File upload = new File(docBase + fileName);
            //判断文件父目录是否存在
            if (!upload.getParentFile().exists()) {
                System.out.print("创建上级目录。。。");
                upload.getParentFile().mkdir();
            }
            out = new FileOutputStream(upload);
            byte[] bytes = new byte[1024];
            int i = 0;
            while ((i = in.read(bytes)) != -1){
                out.write(bytes,0,i);
                out.flush();
            }
            return "上传成功,路径：" + filePath + fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null){
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取图片资源
     * fileName: /usr/application/uploadfile/2021-06-29/1624931126685163.jpg
     */
    @GetMapping("/getPhotoSource")
    public void getPhotoSource(@RequestParam(value = "fileName") String fileName, HttpServletResponse response){
        InputStream in = null;
        OutputStream out = null;
        try{
            in = new FileInputStream(new File(fileName));
            out = response.getOutputStream();
            byte[] data = IOUtils.toByteArray(in);
            out.write(data);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(out != null){
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取图片资源
     * fileName: /usr/application/uploadfile/2021-06-29/1624931126685163.jpg
     */
    @GetMapping("/getPhotoSource11")
    public void getPhotoSource11(@RequestParam(value = "fileName") String fileName, HttpServletResponse response){
        InputStream in = null;
        OutputStream out = null;
        try{
            in = new FileInputStream(new File(fileName));
            out = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int i = 0;
            while ((i = in.read(bytes)) != -1){
                out.write(bytes,0,i);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(out != null){
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 下载图片到某一目录下
     */
    @GetMapping("/getPhotoSource22")
    public void getPhotoSource22(@RequestParam(value = "fileName") String fileName){
        InputStream in = null;
        OutputStream out = null;
        try{
            String dir = "D:/abc.jpg";
            in = new FileInputStream(new File(fileName));
            out = new FileOutputStream(new File(dir));
            byte[] bytes = new byte[1024];
            int i = 0;
            while ((i = in.read(bytes)) != -1){
                out.write(bytes,0,i);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(out != null){
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 下载文件 pdf、doc 、excel
     * @param fileName  /usr/application/uploadfile/2021-06-29/1624931298562085.doc
     * @param httpResponse
     * @param httpRequest
     * @throws IOException
     */
    @GetMapping("/download")
    public void download(@RequestParam(value = "fileName", required = true) String fileName,
                         HttpServletResponse httpResponse, HttpServletRequest httpRequest) throws IOException {

        String userAgent = httpRequest.getHeader("User-Agent");
        //解决乱码 IE 8 至 IE 10, IE 11
        if (userAgent.toUpperCase().contains("MSIE") || userAgent.contains("Trident/7.0")) {
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        } else{
            fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
        }

        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(new File(fileName));
            out = httpResponse.getOutputStream();
            httpResponse.reset();
            String suffixName = fileName.substring(fileName.lastIndexOf("/")+1);
            httpResponse.addHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", suffixName));
            httpResponse.setContentType("application/octet-stream;charset=utf-8");
            httpResponse.setCharacterEncoding("UTF-8");
            IOUtils.copy(in, out);
            log.info("文件下载成功");
        } catch (Exception e) {
            log.error("文件下载异常", e);
        } finally {
            if (in != null){
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
