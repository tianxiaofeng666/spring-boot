package com.example.springbootmybatis.dao;

import com.example.springbootmybatis.pojo.Role;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RoleRepository extends ElasticsearchRepository<Role, Integer> {

}
