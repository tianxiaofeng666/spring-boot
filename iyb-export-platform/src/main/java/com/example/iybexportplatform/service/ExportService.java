package com.example.iybexportplatform.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.iybexportplatform.config.CustomCellWriteHandler;
import com.example.iybexportplatform.dao.ExportDao;
import com.example.iybexportplatform.pojo.ExportRecord;
import com.example.iybexportplatform.util.StatusUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
public class ExportService {

    @Autowired
    ExportDao exportDao;

    @Autowired
    ThreadPoolExecutor poolExecutor;

    @Autowired
    RestTemplate restTemplate;

    private static final String path = "com.example.iybexportplatform.pojo.";

    public int insertOne(ExportRecord record){
        return exportDao.insertOne(record);
    }

    public int export(JSONObject json) throws Exception{
        //获取下一个recordId
        int max = exportDao.getMaxId();
        int nextId = max + 1;
        ExportRecord record = new ExportRecord();
        record.setId(nextId);
        record.setQueryUrl(json.getString("queryUrl"));
        record.setParam(json.getString("param"));
        exportDao.insertOne(record);
        //开启一个线程，异步执行导出任务
        poolExecutor.submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(10000);
                asynExport(nextId,json);
            }
        });
        return nextId;
    }

    public void asynExport(int nextId,JSONObject json) throws Exception{
        String fileName = "F:\\excel\\" + System.currentTimeMillis() + ".xlsx";
        //导出类
        String modelType = json.getString("modelType");
        //查询url
        String queryUrl = json.getString("queryUrl");
        //参数
        JSONObject param = json.getJSONObject("param");
        //总条数
        int total = json.getInteger("total");

        String modelName = path + modelType;
        Class<?> modelClass = Class.forName(modelName);
        ExcelWriter excelWriter = EasyExcel.write(fileName, modelClass)
                .registerWriteHandler(new CustomCellWriteHandler()).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();

        //每次查询10条
        int pageSize = 5;
        int loop = total % pageSize == 0 ? total/pageSize : total/pageSize + 1;
        log.info("循环次数：" + loop);
        for(int i=1; i<=loop; i++){
            param.put("pageNum",i);
            param.put("pageSize",pageSize);
            log.info("查询url:{},入参：{}",queryUrl,param);
            JSONObject response = restTemplate.postForObject(queryUrl, param, JSONObject.class);
            log.info("返回：" + response);
            JSONArray resp = response.getJSONObject("result").getJSONArray("records");
            log.info("结果：" + resp);
            List<?> list = JSONObject.parseArray(resp.toJSONString(), modelClass);
            excelWriter.write(list, writeSheet);
        }
        excelWriter.finish();
        exportDao.updateRecord(nextId, StatusUtil.EXPORT_SUCCESS, fileName);
    }
}
