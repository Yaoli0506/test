package com.ksrcb.api.biz.mapper;

import com.ksrcb.api.biz.model.system.Branch;
import com.ksrcb.api.biz.model.system.Role;
import com.ksrcb.api.biz.model.system.User;
import com.ksrcb.api.biz.model.system.UserRole;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *Mapper
 */
public interface UserCenterMapper extends Mapper<User> {

    /**
     * 用户查询
     * @return
     */
    List<User> queryUserList(User user);


    /**
     * 查询是否重名
     * @param username
     * @return
     */
    int qryRepeatUser(String username);

    /**
     * 添加新用户
     * @param user
     * @return
     */
    int addUserInfo(User user);

    /**
     * 角色查询
     * @param role
     * @return
     */
    List<Role> queryRoleList(Role role);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    int updateUserInfo(User user);

    /**
     * 分配权限
     * @param userRole
     * @return
     */
    int addUserRole(UserRole userRole);

    /**
     * 删除用户
     * @param user
     * @return
     */
    int delUser(User user);

    /**
     * 修改角色信息
     * @param role
     * @return
     */
    int updateRoleInfo(Role role);

    /**
     * 删除角色信息
     * @param role
     * @return
     */
    int delRole(Role role);

    /**
     * 查询机构
     * @return
     */
    List<Branch> queryBranchList();

    /**
     * 查询机构是否重名
     * @param branch_no
     * @return
     */
    int qryRepeatBranch(String branch_no);

    /**
     * 新增机构
     * @param branch
     * @return
     */
    int addBranchInfo(Branch branch);

    /**
     * 修改机构
     * @param branch
     * @return
     */
    int updateBranchInfo(Branch branch);

}
