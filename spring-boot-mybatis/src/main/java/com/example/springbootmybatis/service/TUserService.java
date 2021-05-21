package com.example.springbootmybatis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootmybatis.model.TUserQueryModel;
import com.example.springbootmybatis.pojo.TUser;

/**
 * @author xiaofeng
 * @since 2021-05-21 10:57:35
 */
public interface TUserService extends IService<TUser> {

    public int removeByModel(TUserQueryModel queryModel);

    public Page<TUser> queryByModel(TUserQueryModel queryModel);

}
