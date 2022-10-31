package com.ycsx.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycsx.demo.common.Result;
import com.ycsx.demo.entity.RolePermission;
import com.ycsx.demo.entity.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import com.ycsx.demo.service.IRoleService;
import com.ycsx.demo.entity.Role;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;


    // 新增或更新
    @PostMapping
    public boolean save(@RequestBody Role role) {
        return roleService.saveOrUpdate(role);
    }

    // 删除
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return roleService.removeById(id);
    }

    // 查询id
    @GetMapping("/{id}")
    public Role findOne(@PathVariable Integer id) {
        return roleService.getById(id);
    }

    // 分页
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(search)) {
            queryWrapper.like("name", search);
        }
        queryWrapper.orderByDesc("id");
        return Result.success(roleService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @PostMapping("/rolePermission/{roleId}")
    public Result<?> rolePermission(@PathVariable Integer roleId,
                                    @RequestBody List<Integer> permissionIds) {
        // 先删除所有绑定的关系，再把前台传入的权限id数组重新绑定
        roleService.setRolePermission(roleId, permissionIds);
        return Result.success();
    }

    @GetMapping("/rolePermission/{roleId}")
    public Result<?> getRolePermission(@PathVariable Integer roleId) {
        // 先删除所有绑定的关系，再把前台传入的权限id数组重新绑定
        return Result.success(roleService.getRolePermission(roleId));
    }
}

