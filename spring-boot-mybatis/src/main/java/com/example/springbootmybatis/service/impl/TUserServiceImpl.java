package com.example.springbootmybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootmybatis.mapper.TUserMapper;
import com.example.springbootmybatis.model.TUserQueryModel;
import com.example.springbootmybatis.pojo.TUser;
import com.example.springbootmybatis.service.TUserService;
import com.example.springbootmybatis.utils.QueryWrapperUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author xiaofeng
 * @since 2021-05-21 10:57:35
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {

    @Resource
    private TUserMapper mapper;

    public int removeByModel(TUserQueryModel queryModel) {
        QueryWrapper<TUser> queryWrapper = QueryWrapperUtil.createQueryWrapper(queryModel);
        int i = mapper.delete(queryWrapper);
        return i;
    }

    public Page<TUser> queryByModel(TUserQueryModel queryModel) {
        //自定义排序字段，默认 id desc
        /*String[] str = new String[]{"create_time"};
        queryModel.setOrderByDesc(str);*/
        QueryWrapper<TUser> queryWrapper = QueryWrapperUtil.createQueryWrapper(queryModel);
        Page<TUser> page = new Page<>(queryModel.getPageNum(), queryModel.getPageSize());
        Page<TUser> queryPage = mapper.selectPage(page, queryWrapper);
        return queryPage;
    }
}

