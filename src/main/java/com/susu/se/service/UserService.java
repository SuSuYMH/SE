package com.susu.se.service;

import com.susu.se.error.UserNotExistedError;
import com.susu.se.model.users.*;
import com.susu.se.model.*;
import com.susu.se.repository.*;
import com.susu.se.utils.Result;
import com.susu.se.utils.SaltUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private AssistantRepository assistantRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;


    public Result<User> getUser(Integer id){
        Optional<User> user=userRepository.findById(id);
        if(!user.isPresent()){
            return Result.wrapErrorResult("cnm! is not exist!");
        }
        return Result.wrapSuccessfulResult(user.get());
    }

    public Result<List<User>> getAllUser(){
        List<User> userlist = (List<User>) userRepository.findAll();

        Boolean nullornot = userlist.isEmpty();
        return new Result<>(nullornot, userlist);
    }


    public Result<String> deleteById(Integer id){
        Optional<User> userOP=userRepository.findById(id);
        if(!userOP.isPresent()) {
            return Result.wrapErrorResult(new UserNotExistedError());
        }
        User user = userOP.get();
        //根据角色去对应的表中，删除子表，级联删除user表
        switch (user.getRoleId()){
            case 1:
                Administrator administratorByUser = administratorRepository.findAdministratorByUser(user);
                administratorRepository.delete(administratorByUser);
                break;
            case 2:
                Teacher teacher  = teacherRepository.findTeacherByUser(user);
                teacherRepository.delete(teacher);
                break;
            case 3:
                Assistant assistant = assistantRepository.findAssistantByUser(user);
                assistantRepository.delete(assistant);
                break;
            case 4:
                Student student = studentRepository.findStudentByUser(user);
                studentRepository.delete(student);
                break;
            default:
                return Result.wrapSuccessfulResult("用户注销失败！");
        }
        return Result.wrapSuccessfulResult("用户注销成功！");
    }

    //注册业务
    public Result<String> register(String name, String password, Integer roleId, String email){
        User user = new User();
        user.setName(name);
        user.setRoleId(roleId);
        user.setEmail(email);
        //1.生成随机盐
        String salt = SaltUtil.getSalt(8);
        //2.将随机盐保存到数据库
        user.setSalt(salt);
        //3.对明文密码进行md5、salt、hash散列
        Md5Hash md5Hash = new Md5Hash(password,salt,1024);
        //4.把生成的密码赋值给对象
        user.setPassword(md5Hash.toHex());

        //在父表中创建
        userRepository.save(user);

        //根据roleid创建子类的表
        switch (roleId){
            case 1:
                Administrator administrator = new Administrator();
                administrator.setUser(user);
                administratorRepository.save(administrator);
                break;
            case 2:
                Teacher teacher = new Teacher();
                teacher.setUser(user);
                teacherRepository.save(teacher);
                break;
            case 3:
                Assistant assistant = new Assistant();
                assistant.setUser(user);
                assistantRepository.save(assistant);
                break;
            case 4:
                Student student = new Student();
                student.setUser(user);
                studentRepository.save(student);
                break;
            default:
                userRepository.delete(user);
                return Result.wrapSuccessfulResult("创建角色失败！");
        }
        return Result.wrapSuccessfulResult("Register Success！！！");
    }

//    //注册业务
//    public Result<String> register(User user){
//        //1.生成随机盐
//        String salt = SaltUtil.getSalt(8);
//        //2.将随机盐保存到数据库
//        user.setSalt(salt);
//        //3.对明文密码进行md5、salt、hash散列
//        Md5Hash md5Hash = new Md5Hash(user.getPassword(),salt,1024);
//        //4.把生成的密码赋值给对象
//        user.setPassword(md5Hash.toHex());
//
//        //注意这里还要改一改！！！返回标准格式
//        userRepository.save(user);
//        return Result.wrapSuccessfulResult("Register Success！！！");
//    }

    //根据用户名查询，数据库中的行
    public User findByName(String name){
        return userRepository.findUserByName(name);
    }

}
