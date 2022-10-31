package com.ycsx.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycsx.demo.entity.UserRole;
import org.apache.ibatis.annotations.Select;

public interface UserRoleMapper extends BaseMapper<UserRole> {
    @Select("delete from user_role where user_id=#{userId}")
    void deleteByUserRoleId(Long userId);
}
