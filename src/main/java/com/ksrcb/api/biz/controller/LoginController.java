package com.ksrcb.api.biz.controller;

import com.ksrcb.api.biz.model.system.UserInfo;
import com.ksrcb.api.biz.service.LoginService;
import com.ksrcb.api.common.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    /**
     * 用户登录
     * @param username , password
     * @return
     */
    @PostMapping("/login")
    public ResponseBean login(String username , String password){
        String token = loginService.login(username,password);
        return ResponseBean.success(token);
    }

    /**
     * 查询当前登陆用户信息汇总
     * @return
     */
    @GetMapping("/info")
    public ResponseBean info(UserInfo userInfo){
        Map<String, Object> map = (Map<String, Object>) loginService.info(userInfo);
        return ResponseBean.success(map);

    }




}
