package com.susu.se.service;

import com.susu.se.repository.AdministratorRepository;
import com.susu.se.utils.Return.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {
    @Autowired
    private AdministratorRepository administratorRepository;

    //根据id删除用户，这个会级联删除到user表
    public Result<String> deleteById(Integer administratorId){
        administratorRepository.deleteById(administratorId);
        return Result.wrapSuccessfulResult("删除成功！");
    }


}
