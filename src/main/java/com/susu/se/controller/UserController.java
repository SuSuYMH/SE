package com.susu.se.controller;

import com.susu.se.model.users.User;
import com.susu.se.service.UserService;
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
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    //查询单个管理员
    @GetMapping(path="/{id}")
    public Result<User> getAdminister(@PathVariable Integer id){
        return userService.getUser(id);
    }

    //查询所有管理员,先试一下没有返回标准形式
    @GetMapping
    public Result<List<User>> getAll(){
        return userService.getAllUser();
    }

    //按照id删除管理员
    @DeleteMapping(path = "/{id}")
    public Result<String> deleteById(@PathVariable Integer id){ return userService.deleteById(id);}

    //注册
    @PostMapping(path = "/register")
    public Result<String> register(@RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("roleid") Integer roleId, @RequestParam("email") String email){
        return userService.register(name, password, roleId, email);
    }

//    //注册
//    @PostMapping(path = "/register")
//    public Result<String> register(@RequestBody User user){
//        System.out.println(user.toString());
//        System.out.println("____________");
//        return userService.register(user);
//    }

    //登陆
    @PostMapping(path= "/login")
    public Result<String> login(@RequestParam("name") String name, @RequestParam("password") String password){
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //
        User user = new User();
        user.setName(name);
        user.setPassword(password);

        try {
            subject.login(new UsernamePasswordToken(user.getName(), user.getPassword()));
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
}
