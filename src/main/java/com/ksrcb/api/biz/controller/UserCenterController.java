package com.ksrcb.api.biz.controller;

import com.ksrcb.api.biz.model.biz.PageRequest;
import com.ksrcb.api.biz.model.biz.PageResult;
import com.ksrcb.api.biz.model.system.Branch;
import com.ksrcb.api.biz.model.system.Role;
import com.ksrcb.api.biz.model.system.User;
import com.ksrcb.api.biz.service.UserCenterService;
import com.ksrcb.api.common.annotation.ControllerEndpoint;
import com.ksrcb.api.common.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userCenter")
public class UserCenterController {

    @Autowired
    UserCenterService userCenterService;

    /**
     * 用户管理 /用户查询
     * @param
     * @return
     */
    @GetMapping("/userMgmt/queryUserList")
    public ResponseBean queryUserList(PageRequest pageRequest , User user){
        PageResult userMgmtList = userCenterService.queryUserList(pageRequest,user);
        return ResponseBean.success(userMgmtList);
    }

    /**
     * 用户管理 /新增用户
     * @param user
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "添加用户失败", operation = "添加用户")
    @PostMapping("/userMgmt/addUser")
    public ResponseBean addUser(@RequestBody User user) {
        int result = userCenterService.addUser(user);
        return ResponseBean.success(result);
    }

    /**
     * 修改用户信息
     * @param user
     * @param user
     * @return
     */
    @PostMapping("/userMgmt/updateUser")
    public ResponseBean updateUser(@RequestBody User user){
        int result = userCenterService.updateUser(user);
        return ResponseBean.success(result);
    }

    /**
     * 删除用户
     * @param user
     * @return
     */
    @PostMapping("/userMgmt/delUser")
    public ResponseBean delUser(@RequestBody User user){
        int result =  userCenterService.delUser(user);
        return ResponseBean.success(result);

    }

    /**
     * 角色查询 /角色功能权限查询
     * @return
     */
    @GetMapping("/roleMgmt/qryRoleList")
    public ResponseBean queryRole(Role role){
        List<Role> roles  = userCenterService.queryRole(role);
        return ResponseBean.success(roles);
    }

    /**
     * 修改角色信息
     * @return
     */
    @PostMapping("/roleMgmt/updateRole")
    public ResponseBean updateRole(@RequestBody Role role){
        int result =  userCenterService.updateRole(role);
        return ResponseBean.success(result);
    }

    /**
     * 删除角色
     * @param role
     * @return
     */
    @PostMapping("/roleMgmt/delRole")
    public ResponseBean delRole(@RequestBody Role role){
        int result =  userCenterService.delRole(role);
        return ResponseBean.success(result);
    }

    /**
     * 查询机构
     * @return
     */
    @GetMapping("/branchMgmt/qryBranchList")
    public ResponseBean qryBranch(){
        List<Branch> branches = userCenterService.queryBranch();
        return ResponseBean.success(branches);
    }

    /**
     * 新增机构
     * @param branch
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "添加机构失败", operation = "添加机构")
    @PostMapping("/branchMgmt/addBranch")
    public ResponseBean addBranch(@RequestBody Branch branch){
        int result = userCenterService.addBranch(branch);
        return ResponseBean.success(result);
    }


    /**
     * 编辑机构
     * @return
     */
    @PostMapping("/branchMgmt/updateBranch")
    public ResponseBean updateBranch(Branch branch){
        int result =  userCenterService.updateBranch(branch);
        return ResponseBean.success(result);
    }

}
