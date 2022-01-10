package com.susu.se.controller.Permission;

import com.susu.se.model.Permission;
import com.susu.se.service.PermissionService;
import com.susu.se.utils.PermissionIDList;
import com.susu.se.utils.Return.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Action;
import java.util.List;

@RestController
@RequestMapping("permissions")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    //查看某一用户所有权限
    @RequiresPermissions("administrator")
    @GetMapping("/{userID}")
    public Result<List<Permission>> getAllPermissionByUserId(@PathVariable Integer userID){
        return permissionService.getAllPermissionByUserId(userID);
    }

    //修改用户权限
    @RequiresPermissions("administrator")
    @PostMapping("/{userID}")
    public Result<String> alterUserPermission(@PathVariable Integer userID,@RequestBody PermissionIDList permissionIDList){
        return permissionService.alterUserPermission(userID, permissionIDList.getPermissionIDList());
    }

    //获取所有权限
    @RequiresPermissions("administrator")
    @GetMapping()
    public Result<List<Permission>> getAllPerission(){
        return permissionService.getAllPerission();
    }
}
