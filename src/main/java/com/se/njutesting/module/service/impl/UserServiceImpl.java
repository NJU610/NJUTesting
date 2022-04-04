package com.se.njutesting.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.njutesting.module.dao.UserMapper;
import com.se.njutesting.module.entity.User;
import com.se.njutesting.module.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public List<User> selectUserByName(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<User> selectAllUsers() {
        return this.baseMapper.selectList(null);
    }
}
