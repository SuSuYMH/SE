package com.susu.se.controller.Academic;

import com.susu.se.model.Class;
import com.susu.se.model.Course;
import com.susu.se.service.ClassService;
import com.susu.se.utils.Return.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("classes")
public class ClassController {
    @Autowired
    private ClassService classService;

    //添加班级
    @PostMapping(path = "/add")
    public Result<String> addClass(@RequestParam("week") Integer week,@RequestParam("starttime") String startTime,@RequestParam("endtime") String endTime,@RequestParam("room") String room,@RequestParam("reportrate") Integer reportRate,@RequestParam("attendrate") Integer attendRate,@RequestParam("cprate") Integer confrontPracticeRate,@RequestParam("courseid") Integer courseId,@RequestParam("teacherid") Integer teacherId){
        return classService.addClass(week,startTime,endTime,room,reportRate, attendRate, confrontPracticeRate, courseId,teacherId);
    }

    //获取所有班级
    @GetMapping
    public Result<List<Class>> getAll(){
        return classService.getAll();
    }

    //获取某一课程的所有班级
    @GetMapping("/{courseId}")
    public Result<List<Class>> getAllOfCourse(@PathVariable Integer courseId){
        return classService.getAllClassOfCourse(courseId);
    }

    //删除某一班级
    @DeleteMapping("/{classId}")
    public Result<String> deleteOneClass(@PathVariable Integer classId){
        return classService.deleteClass(classId);
    }

    //更新班级信息
    @PostMapping("/alter/{classId}")
    public Result<String> alterOneClass(@RequestParam("week") Integer week,@RequestParam("starttime") String startTime,@RequestParam("endtime") String endTime,@RequestParam("room") String room,@RequestParam("reportrate") Integer reportRate,@RequestParam("attendrate") Integer attendRate,@RequestParam("cprate") Integer confrontPracticeRate, @PathVariable Integer classId){
        return classService.alterCourse(week, startTime, endTime, room,reportRate, attendRate, confrontPracticeRate,  classId);
    }

    //根据班级查课程
    @GetMapping("/getcourse/{classId}")
    public Result<Course> getCourseByClassId(@PathVariable Integer classId){
        return classService.getCourseByClassId(classId);
    }

}
