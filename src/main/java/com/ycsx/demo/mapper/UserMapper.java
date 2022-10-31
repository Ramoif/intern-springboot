package com.ycsx.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycsx.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

//直接继承BaseMapper实现基本CRUD操作
public interface UserMapper extends BaseMapper<User> {
    // 一对多查询
    Page<User> findPage(Page<User> page, @Param("username") String username);

    // 查询详细信息
    Page<User> findInternPage(Page<User> page, @Param("username") String username);

    // 查询用户名
    @Select("select password from user where username=#{username}")
    User selectByName(String username);

    @Update("update user set password = #{newPass} where id = #{userId}")
    int updatePass(Map<String, Object> map);

}
