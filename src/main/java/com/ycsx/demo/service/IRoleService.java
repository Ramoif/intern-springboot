package com.ycsx.demo.service;

import com.ycsx.demo.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ycsxdyf
 * @since 2022-03-05
 */
public interface IRoleService extends IService<Role> {
    void setRolePermission(Integer roleId, List<Integer> permissionIds);

    List<Integer> getRolePermission(Integer roleId);
}
