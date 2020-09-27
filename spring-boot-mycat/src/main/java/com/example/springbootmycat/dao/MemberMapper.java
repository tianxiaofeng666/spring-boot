package com.example.springbootmycat.dao;

import com.example.springbootmycat.pojo.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface MemberMapper {
    @Insert("insert into t_member(id,name) value (#{id},#{name})")
    public int insert(Member member);

    @Select("select id,name from t_member where id = #{id}")
    public Member getOne(long id);
}
