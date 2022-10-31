package com.ycsx.demo.mapper;

import com.ycsx.demo.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycsx.demo.entity.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ycsxdyf
 * @since 2022-03-05
 */
public interface RoleMapper extends BaseMapper<Role> {
    @Select("select * from user_role where user_id = #{userId}")
    List<UserRole> getUserRoleByUserId(Integer userId);

    @Select("select id from role where flag = #{flag}")
    Integer selectByFlag(@Param("flag") String flag);

    @Delete("delete from user_role where user_id = #{userId}")
    void deleteRoleByUserId(Integer userId);

    @Insert("insert into user_role(user_id, role_id) values(#{userId}, #{roleId})")
    void insertUserRole(Integer userId, Integer roleId);
}
