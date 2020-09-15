package com.example.springbootesdemo.service.impl;

import com.example.springbootesdemo.dao.UserDao;
import com.example.springbootesdemo.pojo.User;
import com.example.springbootesdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public boolean createOrUpdateIndex() {
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(User.class);
        indexOperations.createMapping(User.class);
        return indexOperations.create();
    }

    @Override
    public boolean deleteIndex() {
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(User.class);
        return indexOperations.delete();
    }

    @Override
    public User findById(Long id) {
        Optional<User> optional = userDao.findById(id);
        if(optional != null && optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public void deleteById(long id) {
        userDao.deleteById(id);
    }

    @Override
    public List<User> findByNameLike(String name) {
        return userDao.findByNameLike(name);
    }

    @Override
    public List<User> findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public Iterator<User> findAll() {
        return userDao.findAll().iterator();
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void saveAll(List<User> list) {
        userDao.saveAll(list);
    }

    @Override
    public Page<User> findByName(String name, Pageable pageable) {
        return userDao.findByName(name,pageable);
    }
}
