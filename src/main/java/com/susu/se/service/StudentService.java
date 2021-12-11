package com.susu.se.service;

import com.susu.se.repository.StudentRepository;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    //根据id删除用户，这个会级联删除到user表
    public Result<String> deleteById(Integer studentId){
        studentRepository.deleteById(studentId);
        return Result.wrapSuccessfulResult("删除成功！");
    }
}
