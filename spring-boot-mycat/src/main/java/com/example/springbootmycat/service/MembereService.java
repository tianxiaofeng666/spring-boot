package com.example.springbootmycat.service;

import com.example.springbootmycat.pojo.Member;

public interface MembereService {

    public int insert(Member member);

    public Member getOne(long id);

}
