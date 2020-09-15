package com.example.springbootmybatis.service.impl;

import com.example.springbootmybatis.dao.RoleRepository;
import com.example.springbootmybatis.mapper.RoleDao;
import com.example.springbootmybatis.pojo.Role;
import com.example.springbootmybatis.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleDao.getAll();
    }

    @Override
    public Role getOne(int id) {
        //先从ES里面取，如果不存在，再从数据库里面取，然后再存到ES里面
        Role cache = this.findById(id);
        if(cache == null){
            Role role = roleDao.getOne(id);
            if(role == null){
                return null;
            }
            this.save(role);
            return role;
        }else{
            return cache;
        }
    }

    @Override
    @Transactional
    public void insert(Role role) {
        int maxId = roleDao.maxId();
        role.setId(maxId + 1);
        roleDao.insert(role);
        //将数据存到ES里面，下次取从ES里面拿
        this.save(role);
    }

    @Override
    public List<Role> getRoleByCondition(Role role) {
        return roleDao.getRoleByCondition(role);
    }

    @Override
    public Role findById(int id) {
        Optional<Role> optional = roleRepository.findById(id);
        if(optional != null && optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void deleteById(int id) {
        roleRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        roleRepository.deleteAll();
    }
}
