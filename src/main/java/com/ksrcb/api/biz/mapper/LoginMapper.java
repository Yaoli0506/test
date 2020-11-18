package com.ksrcb.api.biz.mapper;

import com.ksrcb.api.biz.model.system.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Mapper
 */
public interface LoginMapper extends Mapper<User> {

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    User qryUserByName(String username);

    /**
     * 根据UserId查询角色
     * @param userId
     * @return
     */
    UserRole qryRoleByUserId(String userId);

    /**
     * 根据角色ID查询功能
     * @param roleId
     * @return
     */
    List<Func> qryfuncsByRoleId(String roleId);


    /**
     * 查询用户汇总信息
     * @param username
     * @return
     */
    UserInfo qryUserInfo(String username);


    /**
     * 查询当前用户角色信息
     * @param request
     * @return
     */
    List<Map<String,Object>> qryRolePermission(Map<String,Object> request);


}
