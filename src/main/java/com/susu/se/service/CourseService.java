package com.susu.se.service;

import com.susu.se.model.Administrator;
import com.susu.se.model.Course;
import com.susu.se.repository.CourseRepository;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Result<Course> getCourse(Integer id){
        Optional<Course> course=courseRepository.findById(id);
        if(!course.isPresent()){
            return Result.wrapErrorResult("Course is not exist!");
        }
        return Result.wrapSuccessfulResult(course.get());
    }
}
