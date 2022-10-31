package com.ycsx.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycsx.demo.common.Result;
import com.ycsx.demo.mapper.PoolCentralizedMapper;
import com.ycsx.demo.service.IContractService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import com.ycsx.demo.service.IPoolCentralizedService;
import com.ycsx.demo.entity.PoolCentralized;

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
@RequestMapping("/pool")
public class PoolCentralizedController {

    @Resource
    private IPoolCentralizedService poolCentralizedService;

    @Resource
    private PoolCentralizedMapper poolCentralizedMapper;

    // 新增或更新
    @PostMapping
    public boolean save(@RequestBody PoolCentralized poolCentralized) {
        return poolCentralizedService.saveOrUpdate(poolCentralized);
    }

    // 删除
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return poolCentralizedService.removeById(id);
    }

    // 查询所有
    @GetMapping
    public List<PoolCentralized> findAll() {
        return poolCentralizedService.list();
    }

    // 查询id
    @GetMapping("/{id}")
    public PoolCentralized findOne(@PathVariable Integer id) {
        return poolCentralizedService.getById(id);
    }

    // 分页
    @GetMapping("/page")
    public Page<PoolCentralized> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return poolCentralizedService.page(new Page<>(pageNum, pageSize));
    }

    // 条件查询分页
    @GetMapping("/search")
    public Result<?> findPoolPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<PoolCentralized> wrapper = Wrappers.<PoolCentralized>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.eq(PoolCentralized::getCurrentState, search);
        }
        Page<PoolCentralized> poolCentralizedPage = poolCentralizedMapper.findCurrentStatePage(new Page<>(pageNum, pageSize), search);
        return Result.success(poolCentralizedPage);
    }

    // +1当前容量
    @PostMapping("/sizeAdd/{id}")
    public Result<?> PoolSwitch(@PathVariable Integer id) {
        return Result.success(poolCentralizedMapper.quantityPlus(id));
    }

}

