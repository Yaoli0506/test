package com.ksrcb.api.biz.mapper;

import com.ksrcb.api.biz.model.system.Menu;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Mapper
 */
public interface MenuMapper extends Mapper<Menu> {

    /**
     * 查询当前用户所有功能
     * @param roleId
     * @return
     */
    List<Map<String, Object>> qryMenu(String roleId);
}
