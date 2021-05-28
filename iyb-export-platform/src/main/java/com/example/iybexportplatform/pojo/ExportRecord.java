package com.example.iybexportplatform.pojo;

import lombok.Data;

@Data
public class ExportRecord {
    private int id;
    private String queryUrl;
    private String param;
    private int status;
    private String path;
    private String isDelete;
    private String creator;
    private String createTime;
    private String modifier;
    private String updateTime;
}
