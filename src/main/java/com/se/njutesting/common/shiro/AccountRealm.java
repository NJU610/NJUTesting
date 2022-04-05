package com.se.njutesting.common.shiro;

import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.se.njutesting.common.util.JwtUtils;
import com.se.njutesting.module.entity.User;
import com.se.njutesting.module.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    private IUserService userService;
    @Autowired
    JwtUtils jwtUtils;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (((User) principalCollection.getPrimaryPrincipal()).getId().equals(1)) {
            info.addRole("admin");
            info.addStringPermission("admin:add");
        }
        info.addRole("user");
        info.addStringPermission("user:visitor");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authenticationToken;
        String userId = (String) jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).get("userId");
        String username = (String) jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).get("username");

        List<User> users = userService.list(new QueryWrapper<User>().eq("username", username));
        if (users.size() == 0) throw new AuthenticationException("wrong username!");
        User user = users.get(0);
        if (!Objects.equals(user.getId(), Integer.valueOf(userId))) throw new AuthenticationException("wrong id!");

        return new SimpleAuthenticationInfo(user, jwtToken.getCredentials(), getName());
    }
}
