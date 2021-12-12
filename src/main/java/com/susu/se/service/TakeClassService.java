package com.susu.se.service;

import com.susu.se.model.Class;
import com.susu.se.model.TakeClass;
import com.susu.se.model.users.Student;
import com.susu.se.repository.ClassRepository;
import com.susu.se.repository.StudentRepository;
import com.susu.se.repository.TakeClassRepository;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TakeClassService {
    @Autowired
    private TakeClassRepository takeClassRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    //参加课程-----增
    public Result<String> takeClassByStudentIDAndClassID(Integer studentId, Integer classId){
        Optional<Student> byId = studentRepository.findById(studentId);
        Student student = byId.get();
        Optional<Class> byId1 = classRepository.findById(classId);
        Class aClass = byId1.get();

        TakeClass studentTakeClass = new TakeClass();
        studentTakeClass.setStudent(student);
        studentTakeClass.setKecheng(aClass);

        takeClassRepository.save(studentTakeClass);
        return Result.wrapSuccessfulResult("参加课程成功！");
    }

    //取消参加课程-------删
    public Result<String> leaveClassByStudentIDAndClassID(Integer studentId, Integer classId){
        Optional<Student> byId = studentRepository.findById(studentId);
        Student student = byId.get();
        Optional<Class> byId1 = classRepository.findById(classId);
        Class aClass = byId1.get();

        TakeClass takeClassByStudentAndAClass = takeClassRepository.findTakeClassByStudentAndKecheng(student, aClass);
        takeClassRepository.delete(takeClassByStudentAndAClass);

        return Result.wrapSuccessfulResult("取消参加课程成功！");
    }

    //查看某一学生的选的所有课程-------查
    public Result<List<Class>> getAllClassByStudentId(Integer studentId){
        Optional<Student> byId = studentRepository.findById(studentId);
        Student student = byId.get();
        List<TakeClass> takeClassByStudent = takeClassRepository.findTakeClassesByStudent(student);
        List<Class> classByStudent = new ArrayList<>();
        for(TakeClass oneTake : takeClassByStudent){
            classByStudent.add(oneTake.getKecheng());
        }
        return Result.wrapSuccessfulResult(classByStudent);
    }

    //查看某一班级所有的选课学生--------查
    public Result<List<Student>> getAllStudentByClassId(Integer classId){
        Optional<Class> byId1 = classRepository.findById(classId);
        Class aClass = byId1.get();
        List<TakeClass> takeClassByAClasses = takeClassRepository.findTakeClassesByKecheng(aClass);
        List<Student> studentsByClass = new ArrayList<>();
        for(TakeClass oneTake: takeClassByAClasses){
            studentsByClass.add(oneTake.getStudent());
        }
        return Result.wrapSuccessfulResult(studentsByClass);
    }
}
