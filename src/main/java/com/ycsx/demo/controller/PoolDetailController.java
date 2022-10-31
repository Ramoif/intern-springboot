package com.ycsx.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycsx.demo.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import com.ycsx.demo.service.IPoolDetailService;
import com.ycsx.demo.entity.PoolDetail;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ycsxdyf
 * @since 2022-04-25
 */
@RestController
@RequestMapping("/pool-detail")
public class PoolDetailController {

    @Resource
    private IPoolDetailService poolDetailService;

    // 新增或更新
    @PostMapping
    public boolean save(@RequestBody PoolDetail poolDetail) {
        return poolDetailService.saveOrUpdate(poolDetail);
    }

    // 删除
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return poolDetailService.removeById(id);
    }

    // 查询所有
    @GetMapping
    public List<PoolDetail> findAll() {
        return poolDetailService.list();
    }

    // 查询id
    @GetMapping("/{id}")
    public PoolDetail findOne(@PathVariable Integer id) {
        return poolDetailService.getById(id);
    }

    // 分页
    @GetMapping("/page")
    public Page<PoolDetail> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        return poolDetailService.page(new Page<>(pageNum, pageSize));
    }

    // 查询已有的选择协议中仍在初始化0的条数，res.data存放条数
    @GetMapping("/queryCounts/{userId}")
    public Result<?> queryCounts(@PathVariable Integer userId) {
        Long counts = poolDetailService.selectCounts(userId);
        return Result.success(counts);
    }

}

