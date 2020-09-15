package com.example.springbootesdemo.service;

import com.example.springbootesdemo.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface UserService {

    public boolean createOrUpdateIndex();

    public boolean deleteIndex();

    public User findById(Long id);

    public void deleteById(long id);

    public List<User> findByNameLike(String name);

    public List<User> findByName(String name);

    public Iterator<User> findAll();

    public void save(User user);

    public void saveAll(List<User> list);

    /**
     * 分页展示
     * @param name
     * @param pageable
     * @return
     */
    public Page<User> findByName(String name, Pageable pageable);

}
