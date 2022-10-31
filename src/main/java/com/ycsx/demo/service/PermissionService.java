package com.ycsx.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycsx.demo.entity.Permission;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    List<Permission> findPermissions(String search);
}
