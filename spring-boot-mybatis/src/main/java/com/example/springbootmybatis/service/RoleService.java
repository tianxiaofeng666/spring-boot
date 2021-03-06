package com.example.springbootmybatis.service;

import com.example.springbootmybatis.pojo.Role;
import java.util.List;

public interface RoleService {

    public List<Role> getAll();

    public Role getOne(int id);

    public void insert(Role role);

    public List<Role> getRoleByCondition(Role role);

    public Role findById(int id);

    public void save(Role role);

    public void deleteById(int id);

    public void deleteAll();

}
