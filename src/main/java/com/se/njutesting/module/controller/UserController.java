package com.se.njutesting.module.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.se.njutesting.common.Result;
import com.se.njutesting.common.util.JwtUtils;
import com.se.njutesting.module.entity.User;
import com.se.njutesting.module.service.IUserService;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private IUserService userService;

    @RequiresAuthentication
    @RequestMapping("")
    public Result selectAllUsers() {
        return Result.succ(userService.list());
    }

    @RequiresAuthentication
    @RequiresRoles("admin")
    @RequiresPermissions("admin:add")
    @RequestMapping("/add")
    public Result addUser(@RequestBody User user) {
        if (!userService.list(new QueryWrapper<User>().eq("username", user.getUsername())).isEmpty())
            return Result.fail("Exist!");
        return Result.succ(userService.saveOrUpdate(user));
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result UserLogin(@RequestBody Map<String, Object> params, HttpServletResponse response) {
        String username = (String) params.get("username");
        String password = (String) params.get("password");
        List<User> users = userService.list(new QueryWrapper<User>().eq("username", username));
        if (users.size() == 0) return Result.fail("wrong username!");
        User user = users.get(0);
        if (!user.pwdValid(password)) return Result.fail("wrong password!");

        String jwt = jwtUtils.generateToken(user.getId(),user.getUsername());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        return Result.succ("success!");
    }

}
