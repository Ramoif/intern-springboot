package com.ycsx.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycsx.demo.common.Result;
import com.ycsx.demo.entity.Place;
import com.ycsx.demo.mapper.PlaceMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/place")
public class PlaceController {

    // 不规范写法
    @Resource
    PlaceMapper PlaceMapper;

    /* 1.1、新增实现 @PostMapping */
    @PostMapping
    public Result<?> save(@RequestBody Place place) {
        PlaceMapper.insert(place);
        return Result.success();
    }

    /* 1.2、更新实现 @PutMapping */
    @PutMapping
    public Result<?> update(@RequestBody Place place) {
        PlaceMapper.updateById(place);
        return Result.success();
    }

    /* 1.3、删除实现 @DeleteMapping ("/{id}")对应参数@PathVariable Long id */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        PlaceMapper.deleteById(id);
        return Result.success();
    }

    /* 1.4、分页查询 @GetMapping*/
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Place> wrapper = Wrappers.<Place>lambdaQuery().like(Place::getName, search);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Place::getName, search);
        }
        Page<Place> PlacePage = PlaceMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(PlacePage);
    }


}
