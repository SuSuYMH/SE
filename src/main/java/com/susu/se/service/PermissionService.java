package com.susu.se.service;

import com.susu.se.model.Permission;
import com.susu.se.model.UserVSPermission;
import com.susu.se.model.users.User;
import com.susu.se.repository.PermissionRepository;
import com.susu.se.repository.UserRepository;
import com.susu.se.repository.UserVSPermissionRepository;
import com.susu.se.utils.Return.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserVSPermissionRepository userVSPermissionRepository;

    //获取所有权限
    public Result<List<Permission>> getAllPerission(){
        return Result.wrapSuccessfulResult(permissionRepository.findAll());
    }

    //为某个用户赋初始权限
    public Result<String> setInitialPermission(Integer userId,Integer roleId){
        Optional<User> byId = userRepository.findById(userId);
        User user = byId.get();
        List<Permission> permissionsByShouldBelongRoleID = permissionRepository.findPermissionsByShouldBelongRoleID(roleId);
        for(Permission permission:permissionsByShouldBelongRoleID){
            UserVSPermission userVSPermission = new UserVSPermission();
            userVSPermission.setPermission(permission);
            userVSPermission.setUser(user);
            userVSPermissionRepository.save(userVSPermission);
        }
        return Result.wrapSuccessfulResult("赋初始权限成功！").setMessage("赋初始权限成功！");
    }

    //查看某一用户所有权限
    public Result<List<Permission>> getAllPermissionByUserId(Integer userID){
        Optional<User> byId = userRepository.findById(userID);
        User user = byId.get();
        List<Permission> permissions = new ArrayList<>();
        List<UserVSPermission> userVSPermissionsByUser = userVSPermissionRepository.findUserVSPermissionsByUser(user);
        for(UserVSPermission userVSPermission:userVSPermissionsByUser){
            permissions.add(userVSPermission.getPermission());
        }
        return Result.wrapSuccessfulResult(permissions);
    }

    //修改用户权限
    public Result<String> alterUserPermission(Integer userID, List<Integer> permissionIDList){
        Optional<User> byId = userRepository.findById(userID);
        User user = byId.get();
        //先把操作之前的用户对应的权限全部删掉
        List<UserVSPermission> userVSPermissionsByUser = userVSPermissionRepository.findUserVSPermissionsByUser(user);
        for(UserVSPermission userVSPermission:userVSPermissionsByUser){
            userVSPermissionRepository.delete(userVSPermission);
        }
        //再赋予相应权限
        for(Integer permissionId: permissionIDList){
            Optional<Permission> byId1 = permissionRepository.findById(permissionId);
            Permission permission = byId1.get();
            //创建中间表
            UserVSPermission userVSPermission = new UserVSPermission();
            userVSPermission.setUser(user);
            userVSPermission.setPermission(permission);
            userVSPermissionRepository.save(userVSPermission);
        }
        return Result.wrapSuccessfulResult("修改用户权限成功！").setMessage("修改用户权限成功！");
    }
}
