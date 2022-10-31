package com.ycsx.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycsx.demo.common.Constants;
import com.ycsx.demo.entity.Permission;
import com.ycsx.demo.entity.User;
import com.ycsx.demo.entity.UserDTO;
import com.ycsx.demo.exception.CustomException;
import com.ycsx.demo.mapper.RoleMapper;
import com.ycsx.demo.mapper.RolePermissionMapper;
import com.ycsx.demo.mapper.UserMapper;
import com.ycsx.demo.service.IUserService;
import com.ycsx.demo.service.PermissionService;
import com.ycsx.demo.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final Log LOG = Log.get();
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;
    @Resource
    private PermissionService permissionService;


    /**
     * 逻辑举例：role_permission表中的信息
     * | role_id   | permission_id
     * | 1	       | 1
     * | 1	       | 2
     * | 1	       | 3
     * | 1	       | 4
     * | 2	       | 2
     * 要让role_id为1的用户能够访问到permission_id为1、2、3、4的菜单
     * 这里还要处理一级-二级菜单
     */
    @Override
    public User login(User user) {
        User one = getUserInfo(user);
        // 用户存在，继续操作
        if (one != null) {
            // 工具类：深拷贝，避免set get赋值
            BeanUtil.copyProperties(one, user, true);
            // token - 根据ID和Password生成
            String token = TokenUtils.getSignToken(one.getId().toString(), one.getPassword());
            user.setToken(token);
            // 角色权限 - 查到的是字符串，需要转换
            String role = one.getRole();
            // 设置用户的菜单！
            List<Permission> rolePermissions = getRolePermissions(role);
            user.setPermissions(rolePermissions);
            return user;
        } else {
            throw new CustomException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    // 获得用户信息
    private User getUserInfo(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        queryWrapper.eq("password", user.getPassword());
        User one;
        try {
            one = getOne(queryWrapper); // 从数据库查询用户信息
        } catch (Exception e) {
            LOG.error(e);
            throw new CustomException(Constants.CODE_500, "系统错误");
        }
        return one;
    }

    /**
     * 获取当前角色Id所拥有的权限菜单
     * @param roleFlag
     * @return
     */
    private List<Permission> getRolePermissions(String roleFlag){
        Integer roleId = roleMapper.selectByFlag(roleFlag);
        // 当前用户的所有权限路由id集合
        List<Integer> permissionIds = rolePermissionMapper.selectByRoleId(roleId);
        // 查出所有菜单
        List<Permission> permissions = permissionService.findPermissions("");
        // 创建一个存放权限的List
        List<Permission> rolePermissions = new ArrayList<>();
        // 然后再过滤角色的权限路由，查询permission的id在不在上述的Ids中，不在则过滤
        for (Permission permission : permissions) {
            // contains() - 包含，这里的处理方式是不包含!则移除权限路由
            if (permissionIds.contains(permission.getId())) {
                rolePermissions.add(permission);
            }
            List<Permission> children = permission.getChildren();
            // removeIf:删除掉不包含在Ids中的Id
            // 这里为删除掉children中不在Ids中的元素
            children.removeIf(child -> !permissionIds.contains(child.getId()));
        }
        return rolePermissions;
    }
}
