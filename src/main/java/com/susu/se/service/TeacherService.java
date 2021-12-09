package com.susu.se.service;

import com.susu.se.model.Course;
import com.susu.se.model.Teacher;
import com.susu.se.repository.TeacherRepository;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    public Result<Teacher> getTeacher(Integer id){
        Optional<Teacher> teacher=teacherRepository.findById(id);
        if(!teacher.isPresent()){
            return Result.wrapErrorResult("Course is not exist!");
        }
        return Result.wrapSuccessfulResult(teacher.get());
    }

}
