package com.susu.se.controller.Academic;

import com.susu.se.model.Course;
import com.susu.se.service.CourseService;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    //获取所有课程
    @GetMapping
    public Result<List<Course>> getAll(){
        return courseService.getAll();
    }

    //添加课程，要提供必要的课程信息和administratorID
    @PostMapping("/add")
    public Result<String> addCourse(@RequestParam("coursename") String courseName,@RequestParam("intro") String intro,@RequestParam("administratorid") Integer administratorId){
        return courseService.addCourse(courseName, intro, administratorId);
    }

    //删除课程
    @DeleteMapping("/{courseId}")
    public Result<String> deleteCourse(@PathVariable Integer courseId){
        return courseService.deleteCourse(courseId);
    }

    //修改课程信息
    @PostMapping("/alter")
    public Result<String> alterCourse(@RequestParam("coursename") String courseName,@RequestParam("intro") String intro,@RequestParam("courseid") Integer courseId){
        return courseService.alterCourse(courseName, intro, courseId);
    }
}
