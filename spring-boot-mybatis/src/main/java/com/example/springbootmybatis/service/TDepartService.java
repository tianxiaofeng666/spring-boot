package com.example.springbootmybatis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootmybatis.model.TDepartQueryModel;
import com.example.springbootmybatis.pojo.TDepart;

/**
 * @author xiaofeng
 * @since 2021-05-21 11:06:23
 */
public interface TDepartService extends IService<TDepart> {

    public int removeByModel(TDepartQueryModel queryModel);

    public Page<TDepart> queryByModel(TDepartQueryModel queryModel);

}
