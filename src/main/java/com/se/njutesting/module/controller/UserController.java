package com.se.njutesting.module.controller;

import com.se.njutesting.module.entity.User;
import com.se.njutesting.module.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("")
    public List<User> selectAllUsers() {
        List<User> users = userService.selectAllUsers();
        return users;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String UserLogin(@RequestBody Map<String, Object> params) {
        String username = (String) params.get("username");
        String password = (String) params.get("password");
        List<User> users = userService.selectUserByName(username);
        if (users.size() == 0) return "wrong username!";
        User user = users.get(0);
        if (!user.pwdValid(password)) return "wrong password!";
        return "success!";
    }

}
