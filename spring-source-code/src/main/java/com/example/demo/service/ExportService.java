package com.example.demo.service;

import com.example.demo.constant.DateTimeConstant;
import com.example.demo.entity.Student;
import com.example.demo.util.ExcelUtils;
import com.example.demo.util.ExportZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shs-cyhlwzytxf
 */
@Slf4j
@Service
public class ExportService {

    @Resource
    private HttpServletResponse response;

    public void exportExcel(){
        try{
            List<Student> list = new ArrayList<>();
            Student stu1 = new Student("张三","男","111222");
            Student stu2 = new Student("李四","女","3334445");
            list.add(stu1);
            list.add(stu2);
            ExcelUtils.exportExcel(list,"学生信息","学生信息", Student.class, String.valueOf(System.currentTimeMillis()),response);
        }catch (Exception e){
            log.error("报错");
        }
    }

    /**
     * 导出图片到压缩包
     */
    public void exportImageZip(){
        try{
            //图片存放路径
            List<String> pathList = Arrays.asList("qrCode/31032719910512222X.png","qrCode/411327199105121111.png","2022-06-19/1655643499340023.jpg");
            Map<String,String> headImgMap = new HashMap<>(16);
            for (int i=0; i<pathList.size(); i++){
                //String url = URLDecoder.decode(pathList.get(i), "UTF-8");
                headImgMap.put("张三" + i,pathList.get(i));
            }
            ExportZipUtil.downloadImage(headImgMap,response);
        }catch (Exception e){
            log.error("报错");
        }
    }

    /**
     * 导出excel和图片到压缩包
     */
    public void exportExcelAndImageZip(){
        try{
            List<Student> list = new ArrayList<>();
            Student stu1 = new Student("张三","男","111222");
            Student stu2 = new Student("李四","女","3334445");
            list.add(stu1);
            list.add(stu2);
            List<String> pathList = Arrays.asList("qrCode/31032719910512222X.png","qrCode/411327199105121111.png","2022-06-19/1655643499340023.jpg","qrCode/李四-31032719910512222X.png");
            Map<String,String> headImgMap = new HashMap<>(16);
            for (int i=0; i<pathList.size(); i++){
                headImgMap.put("张三" + i,pathList.get(i));
            }
            ExportZipUtil.downLoadExcelAndImage(list,"学生信息","学生信息",Student.class, LocalDateTime.now().format(DateTimeConstant.EXPORT_FILENAME_TIME_FORMAT), headImgMap,response);
        }catch (Exception e){
            log.error("报错");
        }
    }
}
