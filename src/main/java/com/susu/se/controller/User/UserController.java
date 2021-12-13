package com.susu.se.controller.User;

import com.susu.se.model.users.User;
import com.susu.se.service.UserService;
import com.susu.se.utils.Return.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    //查询单个
    @GetMapping(path="/{id}")
    public Result<User> getAdminister(@PathVariable Integer id){
        return userService.getUser(id);
    }

    //查询所有管理员,先试一下没有返回标准形式
    @GetMapping
    public Result<List<User>> getAll(){
        return userService.getAllUser();
    }

    //按照id删除
    @DeleteMapping(path = "/{id}")
    public Result<String> deleteById(@PathVariable Integer id){ return userService.deleteById(id);}

    //注册
    @PostMapping(path = "/register")
    public Result<String> register(@RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("roleid") Integer roleId, @RequestParam("email") String email){
        return userService.register(name, password, roleId, email);
    }

    //登陆
    @PostMapping(path= "/login")
    public Result<Integer> login(@RequestParam("name") String name, @RequestParam("password") String password){
//        //获取主体对象
//        Subject subject = SecurityUtils.getSubject();
//        //
//        User user = new User();
//        user.setName(name);
//        user.setPassword(password);
//
//        try {
//            subject.login(new UsernamePasswordToken(user.getName(), user.getPassword()));
//            return Result.wrapSuccessfulResult("登陆成功！");
//        }  catch (UnknownAccountException e) {
//            e.printStackTrace();
//            return Result.wrapErrorResult("用户名错误!");
//        } catch (IncorrectCredentialsException e) {
//            e.printStackTrace();
//            return Result.wrapErrorResult("密码错误!");
//        }catch (Exception e){
//            e.printStackTrace();
//            return Result.wrapErrorResult(e.getMessage());
//        }
        return userService.login(name, password);

    }
}
