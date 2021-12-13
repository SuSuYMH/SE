package com.susu.se.service;

import com.susu.se.model.users.Teacher;
import com.susu.se.repository.TeacherRepository;
import com.susu.se.utils.Return.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    public Result<String> deleteById(Integer teacherId){
        teacherRepository.deleteById(teacherId);
        return Result.wrapSuccessfulResult("删除成功！");
    }



    public Result<Teacher> getTeacher(Integer id){
        Optional<Teacher> teacher=teacherRepository.findById(id);
        if(!teacher.isPresent()){
            return Result.wrapErrorResult("Course is not exist!");
        }
        return Result.wrapSuccessfulResult(teacher.get());
    }

}
