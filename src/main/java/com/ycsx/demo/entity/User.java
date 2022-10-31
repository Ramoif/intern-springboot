package com.ycsx.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

//这个注解是Mybatis-Plus用来跟数据库表名"一一对应"的
@TableName("user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class User {
    //主键，自增 @TableId(value = "id", type = IdType.AUTO)
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String role;

    private String username;

    private String password;

    private String nickName;

    private String email;

    private String cellphone;

    private Integer age;

    private String sex;

    private String avatarUrl;

    // 或使用sql包的Date
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String token;

    // 权限
    @TableField(exist = false)
    private List<Integer> roles;

    // 310权限路由
    @TableField(exist = false)
    private List<Permission> permissions;

}
