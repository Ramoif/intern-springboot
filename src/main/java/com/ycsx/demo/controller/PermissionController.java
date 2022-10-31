package com.ycsx.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycsx.demo.common.Constants;
import com.ycsx.demo.common.Result;
import com.ycsx.demo.entity.Dict;
import com.ycsx.demo.entity.Permission;
import com.ycsx.demo.mapper.DictMapper;
import com.ycsx.demo.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    private PermissionService pmsService;

    @Resource
    private DictMapper dictMapper;

    // 新增或更新
    @PostMapping
    public boolean save(@RequestBody Permission permission) {
        return pmsService.saveOrUpdate(permission);
    }

    // 删除
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return pmsService.removeById(id);
    }

    // 查询id
    @GetMapping("/{id}")
    public Permission findOne(@PathVariable Integer id) {
        return pmsService.getById(id);
    }

    // 分页
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(search)) {
            queryWrapper.like("name", search);
        }
        queryWrapper.orderByDesc("id");
        return Result.success(pmsService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @GetMapping("/all")
    public Result<?> findAll(@RequestParam(defaultValue = "") String search) {
        return Result.success(pmsService.findPermissions(search));
    }

    @GetMapping("/icons")
    public Result<?> getIcons() {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("type", Constants.DICT_TYPE_ICON);
        return Result.success(dictMapper.selectList(wrapper));

    }

}

