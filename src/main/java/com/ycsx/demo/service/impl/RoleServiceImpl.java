package com.ycsx.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ycsx.demo.entity.Role;
import com.ycsx.demo.entity.RolePermission;
import com.ycsx.demo.mapper.RoleMapper;
import com.ycsx.demo.mapper.RolePermissionMapper;
import com.ycsx.demo.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ycsxdyf
 * @since 2022-03-05
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Resource
    private RolePermissionMapper rolePermissionMapper;

    // @Transactional 保证事务同时完成，否则rollback
    @Transactional
    @Override
    public void setRolePermission(Integer roleId, List<Integer> permissionIds) {
        // QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        // wrapper.eq("role_id",roleId);
        // rolePermissionMapper.delete(wrapper);
        rolePermissionMapper.deleteByRoleId(roleId);
        for (Integer permissionId : permissionIds) {
            RolePermission RP = new RolePermission();
            RP.setRoleId(roleId);
            RP.setPermissionId(permissionId);
            rolePermissionMapper.insert(RP);
        }
    }

    @Override
    public List<Integer> getRolePermission(Integer roleId) {
        return rolePermissionMapper.selectByRoleId(roleId);
    }
}
