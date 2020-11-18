package com.ksrcb.api.biz.service;


import com.ksrcb.api.biz.model.system.UserInfo;

import java.util.List;
import java.util.Map;

public interface MenuService {
    /**
     * 查询所有产品
     * @return
     */
    List<Map<String, Object>> queryProduct(UserInfo userInfo);


}
