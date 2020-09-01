package com.example.springbootmybatis.service.impl;

import com.example.springbootmybatis.mapper.RoleDao;
import com.example.springbootmybatis.pojo.Role;
import com.example.springbootmybatis.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;

    @Override
    public List<Role> getAll() {
        return roleDao.getAll();
    }

    @Override
    public Role getOne(int id) {
        return roleDao.getOne(id);
    }

    @Override
    @Transactional
    public void insert(Role role) {
        roleDao.insert(role);
    }

    @Override
    public List<Role> getRoleByCondition(Role role) {
        return roleDao.getRoleByCondition(role);
    }
}
