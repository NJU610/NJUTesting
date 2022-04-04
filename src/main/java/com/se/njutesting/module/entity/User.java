package com.se.njutesting.module.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.Objects;

@Data
@TableName("user")
public class User {

    @TableField("id")
    private int id;
    @TableField("username")
    private String username;
    @TableField("password")
    @Getter(AccessLevel.NONE)
    private String password;

    public boolean pwdValid(String password) {
        return Objects.equals(this.password, password);
    }

}
