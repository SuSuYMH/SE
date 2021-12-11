package com.susu.se.controller;

import com.susu.se.model.Class;
import com.susu.se.model.Experiment;
import com.susu.se.model.Report;
import com.susu.se.service.AppraiseReportService;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reports")
public class AppraiseReportController {
    @Autowired
    private AppraiseReportService appraiseReportService;

    @PostMapping("/appraise")
    public Result<String> appraise(@RequestParam("reportid") Integer reportId,@RequestParam("score") Integer score,@RequestParam("pingyu") String pingYu, @RequestParam("teacherid") Integer teacherId){
        return appraiseReportService.appraiseReport(reportId, score, pingYu, teacherId);
    }

    @GetMapping("/getclassesofyourself/{teacherID}")
    public Result<List<Class>> getAllClassByTeacherId(@PathVariable Integer teacherID){
        return appraiseReportService.getAllClassOfTeacher(teacherID);
    }

    @GetMapping("/getexperimentsofclass/{classId}")
    public Result<List<Experiment>> getAllExperimentByClassId(@PathVariable Integer classId){
        return appraiseReportService.getAllExperimentOfClass(classId);
    }

    @GetMapping("/getreportsofexperiment/{experimentId}")
    public Result<List<Report>> getAllReportByExperimentId(@PathVariable Integer experimentId){
        return appraiseReportService.getAllReportOfExperiment(experimentId);
    }
}
