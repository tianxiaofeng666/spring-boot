package cn.chinaunicom.person.mapper;

import cn.chinaunicom.person.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("insert into user_info(name,age,info) value (#{name},#{age},#{info})")
    public int save(User user);

    @Select("select id,name,age,info from user_info")
    public List<User> getUserList();
}
