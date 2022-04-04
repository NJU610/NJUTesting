package com.se.njutesting.module.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.se.njutesting.module.entity.User;

import java.util.List;

public interface IUserService extends IService<User> {
    List<User> selectUserByName(String username);
    List<User> selectAllUsers();
}
