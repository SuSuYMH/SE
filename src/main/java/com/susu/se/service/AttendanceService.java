package com.susu.se.service;

import com.susu.se.model.Class;
import com.susu.se.model.TakeClass;
import com.susu.se.model.users.Student;
import com.susu.se.repository.ClassRepository;
import com.susu.se.repository.StudentRepository;
import com.susu.se.repository.TakeClassRepository;
import com.susu.se.utils.Return.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {
    @Autowired
    private TakeClassRepository takeClassRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private StudentRepository studentRepository;

    //为班级中的每个学生创建一次签到,给一个班级id
    public Result<String> addAttendanceToAllStudentOfClass(Integer classId){
        //查出class实体
        Optional<Class> byId = classRepository.findById(classId);
        Class BanJi = byId.get();

        //查出这个class的每一个takes，每一个takes对应的是一个student参加的class
        List<TakeClass> takeClassesByKecheng = takeClassRepository.findTakeClassesByKecheng(BanJi);

        //对每一个takes对应的attendance添加一条签到
        for (TakeClass takeClass:takeClassesByKecheng){
            Double num = takeClass.getAllNumOfAttendance()+1;
            takeClass.setAllNumOfAttendance(num);
            takeClassRepository.save(takeClass);
        }

        return Result.wrapSuccessfulResult("wow").setMessage("设置签到成功！");
    }

    //学生签到
    public Result<String> studentAttendClass(Integer studentId, Integer classId){
        Optional<Student> byId = studentRepository.findById(studentId);
        Student student = byId.get();
        Optional<Class> byId1 = classRepository.findById(classId);
        Class aClass = byId1.get();
        TakeClass takeClass = takeClassRepository.findTakeClassByStudentAndKecheng(student, aClass);

        Double numOfAttendance = takeClass.getNumOfAttendance()+1;
        takeClass.setNumOfAttendance(numOfAttendance);

        takeClassRepository.save(takeClass);

        return Result.wrapSuccessfulResult("wow").setMessage("签到成功！");
    }

}
