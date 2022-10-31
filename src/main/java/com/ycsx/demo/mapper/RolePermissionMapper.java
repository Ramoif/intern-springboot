package com.ycsx.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycsx.demo.entity.RolePermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    // 记得@Param注解，delete返回的是int类型。
    @Delete("delete from role_permission where role_id = #{roleId}")
    int deleteByRoleId(@Param("roleId") Integer roleId);

    @Select("select permission_id from role_permission where role_id = #{roleId}")
    List<Integer> selectByRoleId(Integer roleId);
}
