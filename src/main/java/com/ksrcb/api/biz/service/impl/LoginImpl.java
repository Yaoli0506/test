package com.ksrcb.api.biz.service.impl;

import com.ksrcb.api.biz.config.jwt.JWTToken;
import com.ksrcb.api.biz.mapper.*;
import com.ksrcb.api.biz.model.system.*;
import com.ksrcb.api.biz.service.LoginService;
import com.ksrcb.api.biz.util.JWTUtils;
import com.ksrcb.api.biz.util.MD5Utils;
import com.ksrcb.api.common.enums.Const;
import com.ksrcb.api.common.exception.ErrorCodeEnum;
import com.ksrcb.api.common.exception.ServiceException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户登录实现类
 */
@Service
public class LoginImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public User findUserByName(String username) {
        return loginMapper.qryUserByName(username);
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public String login(String username, String password) {
        User us = loginMapper.qryUserByName(username);
        String token;
        if(us != null){
            String salt = us.getSalt();
            //秘钥为盐
            String target = MD5Utils.md5Encryption(password, salt);
            //生成Token
            token = JWTUtils.sign(username, target);
            JWTToken jwtToken = new JWTToken(token);
            try {
                SecurityUtils.getSubject().login(jwtToken);
            } catch (AuthenticationException e) {
                throw new ServiceException(e.getMessage());
            }
        }else {
            throw new ServiceException(ErrorCodeEnum.USER_ACCOUNT_NOT_FOUND);
        }
        return token;
    }

    /**
     * 查询角色权限
     * @param user 用户的角色
     * @return
     */
    @Override
    public List<Func> findMenusByRoles(User user) {
        UserRole userRole = loginMapper.qryRoleByUserId(user.getUser_id());
        List<Func> funcList = loginMapper.qryfuncsByRoleId(userRole.getRole_id());
        return funcList;
    }

    /**
     * 用户信息汇总
     * @return
     */
    @Override
    public Map<String, Object> info(UserInfo userInfo) {
        UserInfo userInfo1 = loginMapper.qryUserInfo(userInfo.getUsername());
        Map<String, Object> request = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        request.put("RoleId",userInfo1.getRole_id());
        request.put("State","A");
        List<Map<String,Object>> permissionList = loginMapper.qryRolePermission(request);
        List<Map<String, Object>> menuList = menuMapper.qryMenu(userInfo1.getRole_id());
        List<Map<String, Object>> parentList = level(menuList);
        for (Map<String, Object> parent : parentList) {
            if(!"".equals(parent.get("children"))){
                List<Map<String, Object>> sonList = (List<Map<String, Object>>) parent.get("children");
                List<Map<String, Object>> perlist = new ArrayList<Map<String, Object>>();
                for (Map<String, Object> permission : permissionList) {
                    String perm = (String) permission.get(Const.FUNCID);
                    for (Map<String, Object> son : sonList) {
                        String sons = (String) son.get(Const.FUNCID);
                        if(perm != null){
                            if(perm .equals(sons)){
                                perlist.add(permission);
                            }
                        }
                        if(perlist.size()>0){
                            son.put("children",perlist);
                        }else{
                            son.put("children","");
                        }
                    }
                }
            }
        }
        map.put("permissionList",parentList);
        map.put("userInfo",userInfo1);
        return  map;
    }

    protected List<Map<String,Object>> level(List<Map<String, Object>> menuList){
        //父级集合
        List<Map<String, Object>> parentList = new ArrayList<Map<String, Object>>();
        if (!menuList.isEmpty()) {
            for (Map<String, Object> prodId : menuList) {
                if (prodId != null) {
                    String parentId = (String) prodId.get(Const.PARENTID);
                    if ("root".equals(parentId)) {
                        parentList.add(prodId);
                    }
                }
            }
            //处理一级菜单以外的结构
            menuList.removeAll(parentList);
            for (Map<String, Object> farther : parentList) {
                String fartherProdId = (String) farther.get(Const.FUNCID);
                List<Map<String, Object>> sonList = new ArrayList<Map<String, Object>>();
                for (Map<String, Object> son : menuList) {
                    if (son != null) {
                        String sonParentId = (String) son.get(Const.PARENTID);
                        if (fartherProdId.equals(sonParentId)) {
                            sonList.add(son);
                        }

                    }
                }
                if (sonList.size() > 0) {
                    farther.put("children", sonList);
                }else{
                    farther.put("children", "");
                }
            }
        }
        return parentList;
    }




}
