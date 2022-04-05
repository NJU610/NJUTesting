package com.se.njutesting.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.njutesting.module.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
}
