package com.susu.se.service;

import com.susu.se.model.Administrator;
import com.susu.se.model.Course;
import com.susu.se.repository.AdministratorRepository;
import com.susu.se.repository.CourseRepository;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    //根据id获取课程，这个好像没有用。。。。
    public Result<Course> getCourse(Integer id){
        Optional<Course> course=courseRepository.findById(id);
        if(!course.isPresent()){
            return Result.wrapErrorResult("Course is not exist!");
        }
        return Result.wrapSuccessfulResult(course.get());
    }

    //获取所有课程
    public Result<List<Course>> getAll(){
        List<Course> all = courseRepository.findAll();
        return Result.wrapSuccessfulResult(all);
    }

    //删除课程，根据课程id
    public Result<String> deleteCourse(Integer courseId){
        courseRepository.deleteById(courseId);
        return Result.wrapSuccessfulResult("删除成功！");
    }

    //增加课程
    public Result<String> addCourse(String courseName, String intro, Integer administratorId){
        Optional<Administrator> administrator = administratorRepository.findById(administratorId);
        //不知道为什噩梦，我就是在这里转一下，你直接course.setAdministrator(administrator.get())就不行，就离谱
        Administrator administrator1 = administrator.get();
        Course course = new Course();
        course.setIntro(intro);
        course.setName(courseName);
        course.setAdministrator(administrator1);
        courseRepository.save(course);
        return Result.wrapSuccessfulResult("添加课程成功！");
    }

    //修改课程信息
    public Result<String> alterCourse(String courseName, String intro, Integer courseId){
        Optional<Course> course = courseRepository.findById(courseId);
        course.get().setName(courseName);
        course.get().setIntro(intro);
        courseRepository.save(course.get());
        return Result.wrapSuccessfulResult("课程资料修改成功！");
    }
}
