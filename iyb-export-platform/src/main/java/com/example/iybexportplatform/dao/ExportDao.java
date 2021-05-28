package com.example.iybexportplatform.dao;

import com.example.iybexportplatform.pojo.ExportRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ExportDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int insertOne(ExportRecord record){
        return jdbcTemplate.update("insert into t_export_record (query_url,param,create_time,update_time) values (?,?,now(),now())",
                record.getQueryUrl(),record.getParam());
    }

    public int getMaxId(){
        return jdbcTemplate.queryForObject("select ifnull(max(id),0) from t_export_record",Integer.class);
    }

    public int updateRecord(int id, int status, String path){
        return jdbcTemplate.update("update t_export_record set status = ?,path = ?,update_time = now() where id = ?",status,path,id);
    }
}
