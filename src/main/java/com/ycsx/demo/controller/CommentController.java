package com.ycsx.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycsx.demo.common.Result;
import com.ycsx.demo.entity.Comment;
import com.ycsx.demo.mapper.CommentMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/comment")
public class CommentController {

    // 不规范写法
    @Resource
    CommentMapper commentMapper;

    /* 1.1、新增实现 @PostMapping */
    @PostMapping
    public Result<?> save(@RequestBody Comment comment) {
        commentMapper.insert(comment);
        return Result.success();
    }

    /* 1.2、更新实现 @PutMapping */
    @PutMapping
    public Result<?> update(@RequestBody Comment comment) {
        commentMapper.updateById(comment);
        return Result.success();
    }

    /* 1.3、删除实现 @DeleteMapping ("/{id}")对应参数@PathVariable Long id */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        commentMapper.deleteById(id);
        return Result.success();
    }

    /* 1.4、分页查询 @GetMapping*/
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Comment> wrapper = Wrappers.<Comment>lambdaQuery().like(Comment::getTitle, search);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Comment::getTitle, search);
        }
        Page<Comment> PlacePage = commentMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(PlacePage);
    }


}
