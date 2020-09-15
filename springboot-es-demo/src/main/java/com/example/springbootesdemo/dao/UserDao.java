package com.example.springbootesdemo.dao;

import com.example.springbootesdemo.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserDao extends ElasticsearchRepository<User, Long> {

    public List<User> findByNameLike(String name);

    public List<User> findByName(String name);

    public Page<User> findByName(String name, Pageable pageable);

}
