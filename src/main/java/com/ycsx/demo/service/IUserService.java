package com.ycsx.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycsx.demo.entity.User;
import com.ycsx.demo.entity.UserDTO;
import org.springframework.stereotype.Service;

public interface IUserService extends IService<User> {
    User login(User user);
}
