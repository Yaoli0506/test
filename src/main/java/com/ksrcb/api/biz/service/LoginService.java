package com.ksrcb.api.biz.service;


import com.ksrcb.api.biz.model.system.Func;
import com.ksrcb.api.biz.model.system.User;
import com.ksrcb.api.biz.model.system.UserInfo;

import java.util.List;
import java.util.Map;

public interface LoginService {

      /**
       * 根据用户名查询
       * @param username
       * @return
       */
      User findUserByName(String username);

      /**
       * 用户登录
       * @param username
       * @param password
       * @return
       */
      String login(String username , String password);

      /**
       * 根据用户角色查询用户的菜单
       * 菜单: menu+button
       *
       * @param user 用户的角色
       * @return
       */
      List<Func> findMenusByRoles(User user);

      /**
       * 查询登陆用户信息汇总
       * @return
       */
      Map<String, Object> info(UserInfo userInfo);

}
