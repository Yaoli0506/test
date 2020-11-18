package com.ksrcb.api.biz.service;

import com.ksrcb.api.biz.model.biz.PageRequest;
import com.ksrcb.api.biz.model.biz.PageResult;
import com.ksrcb.api.biz.model.system.Branch;
import com.ksrcb.api.biz.model.system.Role;
import com.ksrcb.api.biz.model.system.User;
import jdk.nashorn.internal.ir.BreakableNode;

import java.util.List;

public interface UserCenterService {
    /**
     * 用户查询
     * @return
     */
    PageResult queryUserList(PageRequest pageRequest,User user);

    /**
     * 添加用户
     * @param user
     */
    int addUser(User user);

    /**
     * 用户权限查询
     * @return
     */
    List<Role> queryRole(Role role);

    /**
     * 修改用户信息
     * @param user
     */
    int updateUser(User user);

    /**
     * 删除用户
     * @param user
     * @return
     */
    int delUser(User user);


    /**
     * 修改角色信息
     * @return
     */
    int updateRole(Role role);


    /**
     * 删除角色
     * @param role
     * @return
     */
    int delRole(Role role);

    /**
     * 查询机构
     * @return
     */
    List<Branch> queryBranch();

    /**
     * 新增机构
     * @param branch
     * @return
     */
    int addBranch(Branch branch);

    /**
     * 编辑机构
     * @return
     */
    int updateBranch(Branch branch);
}
