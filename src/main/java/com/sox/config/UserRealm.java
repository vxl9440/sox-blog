package com.sox.config;

import com.sox.pojo.User;
import com.sox.service.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserServiceImpl accountService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        //拿到当前登录对象
        //Subject currentUser = SecurityUtils.getSubject();
        //Account account = (Account)currentUser.getPrincipal();

        return authorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {

        //用户名，密码, 数据库中取
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;

        User user = accountService.getAccountByUsername(token.getUsername());
        //account doesn't exist
        if(user == null) return null;

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute("loginUser", user);

        return new SimpleAuthenticationInfo(currentUser, user.getPassword(),"");
    }
}
