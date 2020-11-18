package com.ksrcb.api.biz.service.impl;

import com.ksrcb.api.biz.mapper.MenuMapper;
import com.ksrcb.api.biz.model.system.Menu;
import com.ksrcb.api.biz.model.system.UserInfo;
import com.ksrcb.api.biz.service.MenuService;
import com.ksrcb.api.common.enums.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Service
public class MenuImpl implements MenuService {
    private static final String home = "home";

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Map<String, Object>> queryProduct(UserInfo userInfo) {
        List<Map<String, Object>> ProductList = menuMapper.qryMenu(userInfo.getRole_id());
        Iterator<Map<String, Object>> it = ProductList.iterator();
        while (it.hasNext()){
            Map<String, Object> map = it.next();
            if (home.equals(map.get(Const.FUNCID))){
                it.remove();
            }
        }
        //父级集合
        List<Map<String, Object>> parentList = new ArrayList<Map<String, Object>>();
        if (!ProductList.isEmpty()) {
            for (Map<String, Object> prodId : ProductList) {
                if (prodId != null) {
                    String parentId = (String) prodId.get(Const.PARENTID);
                    if ("root".equals(parentId)) {
                        parentList.add(prodId);
                    }
                }
            }
            //处理一级菜单以外的结构
            ProductList.removeAll(parentList);

            for (Map<String, Object> farther : parentList) {
                String fartherProdId = (String) farther.get(Const.FUNCID);
                List<Map<String, Object>> sonList = new ArrayList<Map<String, Object>>();
                for (Map<String, Object> son : ProductList) {
                    if (son != null) {
                        String sonParentId = (String) son.get(Const.PARENTID);
                        if (fartherProdId.equals(sonParentId)) {
                            sonList.add(son);
                        }

                    }
                }
                if (sonList.size() > 0) {
                    farther.put("second", sonList);
                }
            }
        } else {
        }
        return parentList;
    }

}
