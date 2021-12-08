package com.susu.se.controller;

import com.susu.se.model.Administrator;
import com.susu.se.service.AdministratorService;
import com.susu.se.utils.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("administrators")
public class AdministratorController {
    @Autowired
    private AdministratorService administerService;

    //查询单个管理员
    @GetMapping(path="/{id}")
    public Result<Administrator> getAdminister(@PathVariable Integer id){
        return administerService.getAdminister(id);
    }

    //查询所有管理员,先试一下没有返回标准形式
    @GetMapping
    public Result<List<Administrator>> getAll(){
        return administerService.getAllAdminister();
    }

    //按照id删除管理员
    @DeleteMapping(path = "/{id}")
    public Result<String> deleteById(@PathVariable Integer id){ return administerService.deleteById(id);}

    //注册
    @PostMapping(path = "/register")
    public Result<String> save(@RequestBody Administrator administrator){
        return administerService.register(administrator);
    }

    //登陆
    @PostMapping(path= "/login")
    public Result<String> login(@RequestBody Administrator administrator){
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //
        try {
            subject.login(new UsernamePasswordToken(administrator.getName(), administrator.getPassword()));
            return Result.wrapSuccessfulResult("登陆成功！");
        }  catch (UnknownAccountException e) {
            e.printStackTrace();
            return Result.wrapErrorResult("用户名错误!");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            return Result.wrapErrorResult("密码错误!");
        }catch (Exception e){
            e.printStackTrace();
            return Result.wrapErrorResult(e.getMessage());
        }

    }

    //test
    @PostMapping(path = "/cnmcnm/{cnm}")
    public Result<Administrator> cnm(@PathVariable String cnm){
        return Result.wrapSuccessfulResult(administerService.findByName(cnm));
    }


}
