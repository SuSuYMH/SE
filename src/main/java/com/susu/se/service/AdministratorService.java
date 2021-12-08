package com.susu.se.service;

import com.susu.se.error.UserNotExistedError;
import com.susu.se.model.Administrator;
import com.susu.se.repository.AdministratorRepository;
import com.susu.se.utils.Result;
import com.susu.se.utils.SaltUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorService {
    @Autowired
    private AdministratorRepository administerRepository;


    public Result<Administrator> getAdminister(Integer id){
        Optional<Administrator> administer=administerRepository.findById(id);
        if(!administer.isPresent()){
            return Result.wrapErrorResult("cnm! is not exist!");
        }
        return Result.wrapSuccessfulResult(administer.get());
    }

    public Result<List<Administrator>> getAllAdminister(){
        List<Administrator> administratorslist = (List<Administrator>) administerRepository.findAll();

        Boolean nullornot = administratorslist.isEmpty();
        return new Result<>(nullornot, administratorslist);
    }


    public Result<String> deleteById(Integer id){
        Optional<Administrator> administrator=administerRepository.findById(id);
        if(!administrator.isPresent()) {
            return Result.wrapErrorResult(new UserNotExistedError());
        }
        administerRepository.deleteById(id);
        return Result.wrapSuccessfulResult("Deleted");
    }

    //注册业务
    public Result<String> register(Administrator administrator){
        //1.生成随机盐
        String salt = SaltUtil.getSalt(8);
        //2.将随机盐保存到数据库
        administrator.setSalt(salt);
        //3.对明文密码进行md5、salt、hash散列
        Md5Hash md5Hash = new Md5Hash(administrator.getPassword(),salt,1024);
        //4.把生成的密码赋值给对象
        administrator.setPassword(md5Hash.toHex());


        //注意这里还要改一改！！！返回标准格式
        administerRepository.save(administrator);
        return Result.wrapSuccessfulResult("Register Success！！！");
    }

    //根据用户名查询，数据库中的行
    public Administrator findByName(String name){
        return administerRepository.findByName(name);
    }

}
