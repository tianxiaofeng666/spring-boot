package com.example.springbootmybatis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootmybatis.bean.RestfulResponse;
import com.example.springbootmybatis.model.TDepartQueryModel;
import com.example.springbootmybatis.pojo.TDepart;
import com.example.springbootmybatis.service.TDepartService;
import com.example.springbootmybatis.utils.Common;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xiaofeng
 * @since 2021-05-21 11:06:23
 */
@RestController
@RequestMapping("/depart")
public class TDepartController {

    @Resource
    private TDepartService service;

    @PostMapping("/add")
    public RestfulResponse add(@RequestBody TDepart entity) {
        try {
            boolean b = service.save(entity);
            if (!b) {
                throw new Exception("add异常");
            }
            return RestfulResponse.success();
        } catch (Exception ex) {
            Common.logger.error("add", ex);
            return RestfulResponse.failed(ex.getMessage());
        }
    }

    @PostMapping("/delete")
    public RestfulResponse delete(@RequestBody TDepartQueryModel queryModel) {
        try {
            Common.letFieldNullIfEmpty(queryModel);
            service.removeByModel(queryModel);
            return RestfulResponse.success();
        } catch (Exception ex) {
            Common.logger.error("delete ", ex);
            return RestfulResponse.failed(ex.getMessage());
        }
    }

    @PostMapping("/update")
    public RestfulResponse update(@RequestBody TDepart entity) {
        try {
            service.updateById(entity);
            return RestfulResponse.success();
        } catch (Exception ex) {
            Common.logger.error("update ", ex);
            return RestfulResponse.failed(ex.getMessage());
        }
    }

    @PostMapping("/query")
    public RestfulResponse query(@RequestBody TDepartQueryModel queryModel) {
        try {
            Common.letFieldNullIfEmpty(queryModel);
            Page<TDepart> page = service.queryByModel(queryModel);
            return RestfulResponse.success(page);
        } catch (Exception ex) {
                Common.logger.error("query", ex);
            return RestfulResponse.failed(ex.getMessage());
        }
    }
}

