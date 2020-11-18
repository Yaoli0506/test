package com.ksrcb.api.biz.config.shiro;


import com.ksrcb.api.biz.config.jwt.JWTToken;
import com.ksrcb.api.biz.model.system.Func;
import com.ksrcb.api.biz.model.system.Menu;
import com.ksrcb.api.biz.model.system.Role;
import com.ksrcb.api.biz.model.system.User;
import com.ksrcb.api.biz.service.LoginService;
import com.ksrcb.api.biz.util.JWTUtils;
import com.ksrcb.api.common.bean.ActiveUser;
import com.ksrcb.api.common.enums.Const;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();

        if("S".equals(activeUser.getUser().getUser_type())){
            authorizationInfo.addStringPermission("*:*");
        }else {
            List<String> permissions = new ArrayList<>(activeUser.getPermissions());
            List<Role> roleList = activeUser.getRoles();
            //授权角色
            if (!CollectionUtils.isEmpty(roleList)) {
                for (Role role : roleList) {
                    authorizationInfo.addRole(role.getDescription());
                }
            }
            //授权权限
            if (!CollectionUtils.isEmpty(permissions)) {
                for (String  permission : permissions) {
                    if (permission != null && !"".equals(permission)) {
                        authorizationInfo.addStringPermission(permission);
                    }
                }
            }
        }
        return authorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtils.getUsername(token);

        if (username == null) {
            throw new AuthenticationException(" token错误，请重新登入！");
        }

        User userBean = loginService.findUserByName(username);

        if (userBean == null) {
            throw new AccountException("账号不存在!");
        }
        if(JWTUtils.isExpire(token)){
            throw new AuthenticationException(" token过期，请重新登入！");
        }

        if (! JWTUtils.verify(token, username, userBean.getPassword())) {
            throw new CredentialsException("密码错误!");
        }

        if(Const.S.equals(userBean.getState())){
            throw new LockedAccountException("账号已被锁定!");
        }

        List<Func> funcs = loginService.findMenusByRoles(userBean);

        Set<String> urls=new HashSet<>();
        if(!CollectionUtils.isEmpty(funcs)){
            for (Func func : funcs) {
                String url = func.getFunc_id();
                if(Const.A.equals(func.getState()) && !StringUtils.isEmpty(url)){
                    urls.add(func.getFunc_id());
                }
            }
        }
        //过滤出url,和用户的权限
        ActiveUser activeUser = new ActiveUser();
        activeUser.setUser(userBean);
        activeUser.setFuncs(funcs);
        activeUser.setUrls(urls);
        return new SimpleAuthenticationInfo(activeUser, token, getName());
    }
}
