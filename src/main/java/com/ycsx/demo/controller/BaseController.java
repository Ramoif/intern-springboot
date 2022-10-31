package com.ycsx.demo.controller;

import com.auth0.jwt.JWT;
import com.ycsx.demo.entity.User;
import com.ycsx.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BaseController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HttpServletRequest request;

    /**
     * 根据token获取用户信息
     * @return
     */
    public User getUser(){
        String token = request.getHeader("token");
        String aud = JWT.decode(token).getAudience().get(0);
        Integer userId = Integer.valueOf(aud);
        return userMapper.selectById(userId);
    }
}