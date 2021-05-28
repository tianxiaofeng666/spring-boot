package com.example.iybexportplatform.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.iybexportplatform.pojo.ExportRecord;
import com.example.iybexportplatform.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExportController {

    @Autowired
    ExportService exportService;

    @RequestMapping("/export")
    public JSONObject export(@RequestBody JSONObject json){
        JSONObject obj = new JSONObject();
        try{
            int result = exportService.export(json);
            obj.put("result","success");
            obj.put("content",result);
        }catch (Exception e){
            e.printStackTrace();
            obj.put("result","fail");
            obj.put("content","导出失败");
        }
        return obj;
    }


}
