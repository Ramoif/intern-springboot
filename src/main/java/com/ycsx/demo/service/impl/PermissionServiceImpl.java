package com.ycsx.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycsx.demo.entity.Permission;
import com.ycsx.demo.mapper.PermissionMapper;
import com.ycsx.demo.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Override
    public List<Permission> findPermissions(String search) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        if(StrUtil.isNotBlank(search)){
            queryWrapper.like("name",search);
        }
        // 查询所有
        List<Permission> list = list(queryWrapper);
        // 找出pid为null的一级菜单
        List<Permission> parentNode = list.stream()
                .filter(permission -> permission.getPid() == null)
                .collect(Collectors.toList());
        // 找出一级菜单的子菜单
        for (Permission permission : parentNode) {
            // 记得这里flip（注意顺序，pid不一定存在，这里用equals注意）
            permission.setChildren(list.stream()
                    .filter(perm -> permission.getId().equals(perm.getPid()))
                    .collect(Collectors.toList()));
        }
        return parentNode;
    }
}
