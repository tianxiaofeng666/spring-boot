package com.example.springbootmybatis.mapper;

import com.example.springbootmybatis.pojo.Role;

import java.util.List;

public interface RoleDao {
    /**
     * 获取所有角色
     */
    public List<Role> getAll();

    public Role getOne(int id);

    public void insert(Role role);

    public List<Role> getRoleByCondition(Role role);
}
