package com.susu.se.service;

import com.susu.se.error.UserNotExistedError;
import com.susu.se.model.users.*;
import com.susu.se.repository.*;
import com.susu.se.utils.Return.ID;
import com.susu.se.utils.Return.Result;
import com.susu.se.utils.SaltUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
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

    //登陆业务
    public Result<ID> login(String name, String password){
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setActivation(false);

        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getName(), user.getPassword());
            subject.login(usernamePasswordToken);

            //返回用户对应的具体的角色表的id
            User userByName = userRepository.findUserByName(name);
            Integer roleId = userByName.getRoleId();
            //根据roleId和user,查出角色表中的id
            switch (roleId){
                case 1:
                    Administrator administratorByUser = administratorRepository.findAdministratorByUser(userByName);
                    ID id = new ID();
                    id.setUserId(userByName.getUserId());
                    id.setSpecialId(administratorByUser.getAdministerId());
                    return Result.wrapSuccessfulResult(id).setMessage("登陆成功！");
                case 2:
                    Teacher teacherByUser = teacherRepository.findTeacherByUser(userByName);
                    ID id1 = new ID();
                    id1.setUserId(userByName.getUserId());
                    id1.setSpecialId(teacherByUser.getTeacherId());
                    return Result.wrapSuccessfulResult(id1).setMessage("登陆成功！");
                case 3:
                    Assistant assistantByUser = assistantRepository.findAssistantByUser(userByName);
                    ID id2 = new ID();
                    id2.setUserId(userByName.getUserId());
                    id2.setSpecialId(assistantByUser.getAssistantId());
                    return Result.wrapSuccessfulResult(id2).setMessage("登陆成功！");
                case 4:
                    Student studentByUser = studentRepository.findStudentByUser(userByName);
                    ID id3 = new ID();
                    id3.setUserId(userByName.getUserId());
                    id3.setSpecialId(studentByUser.getStudentId());
                    return Result.wrapSuccessfulResult(id3).setMessage("登陆成功！");
                default:
                    userRepository.delete(user);
                    return Result.wrapErrorResult("创建角色失败！");
            }
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

    //根据用户名查询，数据库中的行
    public User findByName(String name){
        return userRepository.findUserByName(name);
    }

}
