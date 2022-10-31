package com.ycsx.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("role_permission")
@Data
public class RolePermission {
    private Integer roleId;
    private Integer permissionId;
}
