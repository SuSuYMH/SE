package com.susu.se.service.Board;

import com.susu.se.model.Class;
import com.susu.se.model.TakeClass;
import com.susu.se.model.users.Student;
import com.susu.se.repository.StudentRepository;
import com.susu.se.repository.ClassRepository;
import com.susu.se.repository.TakeClassRepository;
import com.susu.se.utils.Return.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private TakeClassRepository takeClassRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    //返回所有feedback字段不为空的takeclass
    public Result<List<TakeClass>> getAllTakeClassFeedback(){
        List<TakeClass> takeClasses=new LinkedList<>();
        for (TakeClass takeclass : takeClassRepository.findAll()){
            if(takeclass.getFeedback()!=null){
                takeClasses.add(takeclass);
            }
        }
        return Result.wrapSuccessfulResult(takeClasses);
    }

    //填写feedback字段
    public Result<String> writeInFeedback(Integer studentId, Integer classId, String feedback){
        Optional<Student> byId = studentRepository.findById(studentId);
        Student student = byId.get();
        Optional<Class> byId1 = classRepository.findById(classId);
        Class aClass = byId1.get();
        TakeClass takeClassByStudentAndKecheng = takeClassRepository.findTakeClassByStudentAndKecheng(student, aClass);
        takeClassByStudentAndKecheng.setFeedback(feedback);
        takeClassByStudentAndKecheng.setFeedbackTime(new Date());
        takeClassRepository.save(takeClassByStudentAndKecheng);
        return Result.wrapSuccessfulResult("填写反馈信息成功！");
    }
}
