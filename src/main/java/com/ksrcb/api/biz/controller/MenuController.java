package com.ksrcb.api.biz.controller;

import com.ksrcb.api.biz.model.system.UserInfo;
import com.ksrcb.api.common.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ksrcb.api.biz.service.MenuService;

import java.util.List;
import java.util.Map;


@RestController
public class MenuController {

    @Autowired
    MenuService menuService;

    @GetMapping("/menu")
    public ResponseBean queryProduct(UserInfo userInfo){
        List<Map<String, Object>> ProductList = menuService.queryProduct(userInfo);
        return  ResponseBean.success(ProductList);
    }

}
