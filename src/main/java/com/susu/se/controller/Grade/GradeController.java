package com.susu.se.controller.Grade;

import com.susu.se.service.GradeService;
import com.susu.se.utils.Return.ExperimentMeanScore;
import com.susu.se.utils.Return.Result;
import com.susu.se.utils.Return.SingleStudentGradeUtil;
import com.susu.se.utils.Return.StudentGradeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("grades")
public class GradeController {
    @Autowired
    private GradeService gradeService;

    @GetMapping
    public Result<Double> getGrade(@RequestParam("studentid") Integer studentId, @RequestParam("classid") Integer classId){
        return gradeService.getGradeByStudentIdAndClassId(studentId, classId);
    }

    @GetMapping("new")
    public Result<SingleStudentGradeUtil> newGetGrade(@RequestParam("studentid") Integer studentId,@RequestParam("classid") Integer classId){
        return gradeService.newGetGradeByStudentIdAndClassId(studentId, classId);
    }

    //获取班级内每个学生的成绩
    @GetMapping("studentgrade/{classID}")
    public Result<List<StudentGradeUtil>> getAllStudentsGradeByClassId(@PathVariable Integer classID){
        return gradeService.getAllStudentGradeByClassID(classID);
    }

    @GetMapping("experimentmeanscore/{classID}")
    //根据班级ID获取每个实验的平均分
    public Result<List<ExperimentMeanScore>> get(@PathVariable Integer classID){
        return gradeService.getAllExperimentMeanScoreByClassID(classID);
    }
}
