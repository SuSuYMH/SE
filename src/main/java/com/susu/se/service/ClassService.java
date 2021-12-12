package com.susu.se.service;

import com.susu.se.model.*;
import com.susu.se.model.Class;
import com.susu.se.model.users.Teacher;
import com.susu.se.repository.ClassRepository;
import com.susu.se.repository.CourseRepository;
import com.susu.se.repository.TeacherRepository;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    //获取所有班级
    public Result<List<Class>> getAll(){
        List<Class> all = classRepository.findAll();
        return Result.wrapSuccessfulResult(all);
    }

    //获取某一课程的所有班级
    public Result<List<Class>> getAllClassOfCourse(Integer courseId){
        Optional<Course> byId = courseRepository.findById(courseId);
        List<Class> classesByCourse = classRepository.findClassesByCourse(byId.get());
        return new Result().wrapSuccessfulResult(classesByCourse);
    }

    //删除班级，根据班级id
    public Result<String> deleteClass(Integer classId){
        classRepository.deleteById(classId);
        return Result.wrapSuccessfulResult("删除成功！");
    }

    //增加班级，需要课程id和教师id
    public Result<String> addClass(Integer week, String startTime,String endTime,String room,Integer reportRate, Integer attendRate,Integer confrontPracticeRate,  Integer courseId, Integer teacherId){
        Course course = courseRepository.findById(courseId).get();
        Teacher teacher = teacherRepository.findById(teacherId).get();
        Class aClass = new Class();
        aClass.setRoom(room);
        aClass.setWeek(week);
        aClass.setStartTime(startTime);
        aClass.setEndTime(endTime);
        aClass.setReportRate(reportRate);
        aClass.setAttendRate(attendRate);
        aClass.setConfrontPracticeRate(confrontPracticeRate);
        aClass.setCourse(course);
        aClass.setTeacher(teacher);

        classRepository.save(aClass);
        return Result.wrapSuccessfulResult("添加课程成功！");
    }

    //修改班级信息
    public Result<String> alterCourse(Integer week, String startTime,String endTime,String room,Integer reportRate, Integer attendRate,Integer confrontPracticeRate, Integer classId){
        Class aClass = classRepository.findById(classId).get();
        aClass.setRoom(room);
        aClass.setWeek(week);
        aClass.setStartTime(startTime);
        aClass.setEndTime(endTime);
        aClass.setReportRate(reportRate);
        aClass.setAttendRate(attendRate);
        aClass.setConfrontPracticeRate(confrontPracticeRate);

        classRepository.save(aClass);
        return Result.wrapSuccessfulResult("课程信息修改成功！");
    }
}
