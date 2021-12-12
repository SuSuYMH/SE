package com.susu.se.controller;

import com.susu.se.service.GradeService;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("grades")
public class GradeController {
    @Autowired
    private GradeService gradeService;

    @GetMapping
    public Result<Double> getGrade(@RequestParam("studentid") Integer studentId,@RequestParam("classid") Integer classId){
        return gradeService.getGradeByStudentIdAndClassId(studentId, classId);
    }
}
