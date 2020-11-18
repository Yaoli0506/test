package com.ksrcb.api.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ksrcb.api.biz.mapper.UserCenterMapper;
import com.ksrcb.api.biz.model.biz.PageRequest;
import com.ksrcb.api.biz.model.biz.PageResult;
import com.ksrcb.api.biz.model.system.Branch;
import com.ksrcb.api.biz.model.system.Role;
import com.ksrcb.api.biz.model.system.User;
import com.ksrcb.api.biz.model.system.UserRole;
import com.ksrcb.api.biz.service.UserCenterService;
import com.ksrcb.api.biz.util.MD5Utils;
import com.ksrcb.api.biz.util.PageUtils;
import com.ksrcb.api.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserCenterImpl implements UserCenterService {

    @Autowired
    private UserCenterMapper userCenterMapper;

    /**
     * 用户列表
     * @return
     */
    @Override
    public PageResult queryUserList(PageRequest pageRequest, User user) {
        return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest,user));
    }

    /**
     * 调用分页插件完成分页
     * @param pageRequest
     * @return
     */
    private PageInfo<User> getPageInfo(PageRequest pageRequest,User user) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userCenterMapper.queryUserList(user);
        return new PageInfo<User>(userList);
    }

    /**
     * 添加用户
     * @param user
     */
    @Override
    public int addUser(User user){
        String username = user.getUsername();
        //校验是否重名
        int i = userCenterMapper.qryRepeatUser(username);
        if(i!=0){
            throw new ServiceException("该用户名已被占用");
        }
        String salt= UUID.randomUUID().toString().substring(0,32);
        String userId= UUID.randomUUID().toString().substring(0,32);
        user.setPassword(MD5Utils.md5Encryption(user.getPassword(), salt));//密码
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        user.setCreate_time(date);//创建时间
        user.setUpdate_time(date);//修改时间
        if("3".equals(user.getUser_type())){
            user.setAsso_role("超级管理员");
            user.setUser_type("M");
        }else if("6".equals(user.getUser_type())){
            user.setAsso_role("普通用户");
            user.setUser_type("N");
        }else{
            user.setAsso_role("自定义用户");
            user.setUser_type("S");
        }
        if("3".equals(user.getSystemMgmr())){
            user.setSystemMgmr("1");
        }else{
            user.setSystemMgmr("0");
        }
        user.setSalt(salt);//盐值
        user.setUser_id(userId);//userId
        user.setState("A");//状态为活动 A    P  为停用
        UserRole userRole = new UserRole();
        if("M".equals(user.getUser_type())){
            userRole.setUser_id(user.getUser_id());
            userRole.setRole_id("bank_int_r01");
        }else if ("N".equals(user.getUser_type())){
            userRole.setUser_id(user.getUser_id());
            userRole.setRole_id("bank_int_r02");
        }else if ("S".equals(user.getUser_type())){
            //自定义角色随机roleId
            String roleId= UUID.randomUUID().toString().substring(0,32);
            userRole.setUser_id(user.getUser_id());
            userRole.setRole_id("bank_int_r02");
        }
        int role = userCenterMapper.addUserRole(userRole);
        int result = userCenterMapper.addUserInfo(user);
        if(result < 1 || role < 1){
            throw new ServiceException("用户添加失败");
        }
        return result;
    }

    /**
     * 删除用户
     * @param user
     * @return
     */
    @Override
    public int delUser(User user) {
        String username = "$"+user.getUsername()+"$";
        user.setUsername(username);
        user.setState("P");
        int result = userCenterMapper.delUser(user);
        if(result < 1){
            throw new ServiceException("用户删除失败");
        }
        return result;
    }

    /**
     * 查询角色信息
     * @param role
     * @return
     */
    @Override
    public List<Role> queryRole(Role role) {
        List<Role> roleList = userCenterMapper.queryRoleList(role);
        return roleList;
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Override
    public int updateUser(User user) {
        String salt= UUID.randomUUID().toString().substring(0,32);
        user.setPassword(MD5Utils.md5Encryption(user.getPassword(), salt));//密码
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        user.setUpdate_time(date);//修改时间
        user.setSalt(salt);//盐值
        if("3".equals(user.getSystemMgmr())){
            user.setSystemMgmr("1");
        }else{
            user.setSystemMgmr("0");
        }
        int result = userCenterMapper.updateUserInfo(user);
        if(result < 1){
            throw new ServiceException("用户修改失败");
        }
        return result;
    }

    /**
     * 修改角色信息
     * @param role
     * @return
     */
    @Override
    public int updateRole(Role role) {
        int result = userCenterMapper.updateRoleInfo(role);
        return result;
    }

    /**
     * 删除角色
     * @param role
     * @return
     */
    @Override
    public int delRole(Role role) {
        String description = "$" + role.getDescription() + "$";
        role.setDescription(description);
        role.setState("P");
        int result = userCenterMapper.delRole(role);
        return result;
    }

    /**
     * 查询机构
     * @return
     */
    @Override
    public List<Branch> queryBranch() {
        return userCenterMapper.queryBranchList();
    }

    /**
     * 新增机构
     * @param branch
     * @return
     */
    @Override
    public int addBranch(Branch branch) {
        String branch_no = branch.getBranch_no();
        //校验是否重名
        int i = userCenterMapper.qryRepeatBranch(branch_no);
        if(i != 0){
            throw new ServiceException("该机构名已被占用");
        }
        int result = userCenterMapper.addBranchInfo(branch);
        if(result < 1 ){
            throw new ServiceException("机构添加失败");
        }
        return result;
    }

    /**
     * 编辑机构
     * @param branch
     * @return
     */
    @Override
    public int updateBranch(Branch branch) {
        int result = userCenterMapper.updateBranchInfo(branch);
        return result;
    }

}
