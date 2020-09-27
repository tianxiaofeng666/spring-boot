package com.example.springbootmycat.service.impl;

import com.example.springbootmycat.dao.MemberMapper;
import com.example.springbootmycat.pojo.Member;
import com.example.springbootmycat.service.MembereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MemberServiceImpl implements MembereService {

    @Resource
    private MemberMapper memberMapper;

    @Override
    public int insert(Member member) {
        return memberMapper.insert(member);
    }

    @Override
    public Member getOne(long id) {
        return memberMapper.getOne(id);
    }
}
