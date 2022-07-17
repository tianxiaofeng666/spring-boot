package com.example.demo.model.request;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * @author shs-cyhlwzypf
 * @date 2022/01/20
 */
@Data
public class DownloadExcelReq {

    /**
     * 数据结果list
     */
    private List<?> list;

    /**
     * 第一个sheet的标题
     */
    private String title;

    /**
     * 第一个sheet的名字
     */
    private String sheetName;

    /**
     * 结果集的对象类型
     */
    private Class<?> pojoClass;

    /**
     * 下载的文件名字
     */
    private String fileName;

    /**
     * 要下载的头像路径
     */
    private Map<String, String> headImgMap;


    public DownloadExcelReq() {

    }

    public DownloadExcelReq(List<?> list, String title, String sheetName, Class<?> pojoClass,
                            String fileName, Map<String, String> headImgMap) {
        this.list = list;
        this.title = title;
        this.sheetName = sheetName;
        this.pojoClass = pojoClass;
        this.fileName = fileName;
        this.headImgMap = headImgMap;
    }

}
