package com.ycsx.demo.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycsx.demo.common.Constants;
import com.ycsx.demo.common.Result;
import com.ycsx.demo.entity.*;
import com.ycsx.demo.mapper.PermissionMapper;
import com.ycsx.demo.mapper.RoleMapper;
import com.ycsx.demo.mapper.UserMapper;
import com.ycsx.demo.mapper.UserRoleMapper;
import com.ycsx.demo.service.IUserService;
import com.ycsx.demo.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

// @RestController : 这个注释的作用是返回json格式数据
// @RequestMapping : 定义一个统一的路由user
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserMapper userMapper;
    @Resource
    IUserService userService;
    @Resource
    RoleMapper roleMapper;
    @Resource
    PermissionMapper permissionMapper;
    @Resource
    UserRoleMapper userRoleMapper;

    /**
     * 1.1、新增实现 @PostMapping
     */
    // 定义Post接口，注释()中如果不写任何东西，则代表就是/user
    @PostMapping
    public Result<?> save(@RequestBody User user) {
        // @RequestBody User user : 代表前台传回user对象，则映射为User实体类

        // 如果没有密码，设置一个默认密码
        if (user.getPassword() == null) {
            user.setPassword("123456");
        }
        userMapper.insert(user);
        return Result.success();
    }

    /**
     * 1.2、更新实现 @PutMapping
     */
    @PutMapping
    public Result<?> update(@RequestBody User user) {
        userMapper.updateById(user);
        return Result.success();
    }

    /**
     * 1.3、删除实现 @DeleteMapping 这里("/{id}")对应参数@PathVariable Long id
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        userMapper.deleteById(id);
        return Result.success();
    }


    /**
     * 1.4、分页查询 @GetMapping 这里设置默认值可以直接访问接口
     */

    @GetMapping("/oldPage")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery().like(User::getUsername, search);
        // 这里使用Hutool的工具类isNotBlank()判断非空
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(User::getUsername, search);
        }
        // 这里是Mybatis-Plus的Wrapper构造器功能，使用like模糊查询提供给查询框
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(userPage);
    }

    /**
     * 1.5、分页查询·改·version2 查看详细信息intern
     */
    @GetMapping
    public Result<?> findInternPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery().like(User::getUsername, search);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(User::getUsername, search);
        }
        Page<User> userInternPage = userMapper.findInternPage(new Page<>(pageNum, pageSize), search);
        return Result.success(userInternPage);
    }

    /**
     * 1.6、分页查询·改 @GetMapping 可以进行一对多查询（查看用户评论）
     */
    @GetMapping("/userComment")
    public Result<?> findCommentPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        // 自定义的查询方法
        if (StrUtil.isNotBlank(search)) {
            // 这里需要把前台参数传入进来，实现原来的基本条件查询
            wrapper.like(User::getUsername, search);
        }
        // 后面跟的search就是我们条件查询的参数
        Page<User> userPage = userMapper.findPage(new Page<>(pageNum, pageSize), search);
        return Result.success(userPage);
    }

    /**
     * 1.6、getById
     */
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(userMapper.selectById(id));
    }

    /**
     * 1.7 获取记录数
     */
    @GetMapping("/counts")
    public Result<?> getCounts(){
        return Result.success(userService.count());
    }

    /**
     * 2.1、登录实现(旧版本)
     */
    /**
    @PostMapping("/userLogin")
    public Result<?> login(@RequestBody User userParam) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userParam.getUsername());
        queryWrapper.eq("password", userParam.getPassword());
        User res = userMapper.selectOne(queryWrapper);
        if (res == null) {
            return Result.error("-1", "用户名或密码错误");
        }

        // 根据用户ID查询所有角色权限
        // => 根据roleId从role_permission表查询出所有的permissionId
        // => 根据permissionId查询permission信息
        Integer userId = res.getId();
        List<UserRole> userRoles = roleMapper.getUserRoleByUserId(userId);
        HashSet<Permission> permissionSet = new HashSet<>();
        res.setRoles(userRoles.stream().map(UserRole::getRoleId).distinct().collect(Collectors.toList()));
        for (UserRole userRole : userRoles) {
            List<RolePermission> rolePermissions = permissionMapper.getRolePermissionByRoleId(userRole.getRoleId());
            for (RolePermission rolePermission : rolePermissions) {
                Integer permissionId = rolePermission.getPermissionId();
                Permission permission = permissionMapper.selectById(permissionId);
                permissionSet.add(permission);
            }
        }
        // 排序资源路径
        LinkedHashSet<Permission> sortedSet = permissionSet.stream()
                .sorted(Comparator.comparing(Permission::getId))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        res.setPermissions(sortedSet);

        // 生成token
        String token = TokenUtils.getToken(res);
        res.setToken(token);

        return Result.success(res);
    }*/

    /** 登录方法 version3.0 */
    @PostMapping("/login")
    public Result<?> userRoleLogin(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        User res = userService.login(user);
        return Result.success(res);
    }

    /**
     * 2.2、注册实现
     */
    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        // 注册逻辑：先验证重名问题
        User registerUser = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, user.getUsername()));
        // 若查询到重名，则返回错误
        if (registerUser != null) {
            return Result.error("-1", "用户名重复，请重试！");
        }
        // 若没有密码，则设置默认密码
        if (user.getPassword() == null) {
            user.setPassword("123456");
        }
        if (user.getRole() == null) {
            user.setRole("ROLE_USER");
        }
        userMapper.insert(user);
        return Result.success();
    }

    /**
     * 3.1、批量删除
     */
    // ids是一个Json数组，需要@RequestBody接收
    @PostMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids) {
        // mybatis-plus提供的批量删除方法
        userMapper.deleteBatchIds(ids);
        return Result.success();
    }

    /**
     * 4.1、Excel导出
     */
    @GetMapping("/export")
    public void exportUser(HttpServletResponse response) throws Exception {
        // 1、查询所有用户
        List<User> list = userService.list();
        // 2.1、使用在内存操作-写到浏览器的方式
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 2.2、写出到磁盘
        // ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/User.xlsx");

        // 3、自定义标题别名，把遍历出来的字段标题替换为别名
        // writer.addHeaderAlias("id", "id");
        // writer.addHeaderAlias("username", "用户名");
        // writer.addHeaderAlias("password", "密码");
        // writer.addHeaderAlias("nickName", "昵称");
        // writer.addHeaderAlias("age", "年龄");
        // writer.addHeaderAlias("sex", "性别");
        // writer.addHeaderAlias("email", "电子邮箱");
        // writer.addHeaderAlias("cellphone", "电话");
        // writer.addHeaderAlias("avatarUrl", "头像地址");
        // writer.addHeaderAlias("createTime", "创建时间");
        // writer.addHeaderAlias("updateTime", "修改时间");

        // 4、把list内容一次性写到excel
        writer.write(list, true);
        // 自适应宽度
        writer.autoSizeColumnAll();
        // 5、设置浏览器响应（必须）格式固定
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息导出", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        // 6、输出流
        ServletOutputStream out = response.getOutputStream();
        // 7、把Excel刷新到输出流
        writer.flush(out, true);
        // 8、关闭
        out.close();
        writer.close();
    }

    /**
     * 4.2、excel 导入接口
     */
    @PostMapping("/import")
    public Boolean importUser(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> list = reader.readAll(User.class);
        // 使用新增保存导入的信息
        userService.saveBatch(list);
        return true;
    }

}
