package com.susu.se.controller.Report;

import com.susu.se.model.Report;
import com.susu.se.service.AppraiseReportService;
import com.susu.se.utils.Return.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    private AppraiseReportService appraiseReportService;

    @GetMapping
    public Result<List<Report>> getAllReportOfCourseID(@RequestParam("studentid") Integer studentId,@RequestParam("courseid") Integer courseId){
        return appraiseReportService.getAllReportGrade(studentId, courseId);
    }






}
