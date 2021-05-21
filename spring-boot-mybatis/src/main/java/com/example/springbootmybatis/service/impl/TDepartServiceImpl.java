package com.example.springbootmybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootmybatis.mapper.TDepartMapper;
import com.example.springbootmybatis.model.TDepartQueryModel;
import com.example.springbootmybatis.pojo.TDepart;
import com.example.springbootmybatis.service.TDepartService;
import com.example.springbootmybatis.utils.QueryWrapperUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xiaofeng
 * @since 2021-05-21 11:06:23
 */
@Service
public class TDepartServiceImpl extends ServiceImpl<TDepartMapper, TDepart> implements TDepartService {

    @Resource
    private TDepartMapper mapper;

    public int removeByModel(TDepartQueryModel queryModel) {
        QueryWrapper<TDepart> queryWrapper = QueryWrapperUtil.createQueryWrapper(queryModel);
        int i = mapper.delete(queryWrapper);
        return i;
    }

    public Page<TDepart> queryByModel(TDepartQueryModel queryModel) {
        QueryWrapper<TDepart> queryWrapper = QueryWrapperUtil.createQueryWrapper(queryModel);
        Page<TDepart> page = new Page<>(queryModel.getPageNum(), queryModel.getPageSize());
        Page<TDepart> queryPage = mapper.selectPage(page, queryWrapper);
        return queryPage;
    }

}

