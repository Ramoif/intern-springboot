package com.ycsx.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@TableName("permission")
@Getter
@Setter
public class Permission {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer pid;
    private String name;
    private String path;
    private String pagePath;
    private String icon;
    private String description;
    @TableField(exist = false)
    private List<Permission> children;
}
