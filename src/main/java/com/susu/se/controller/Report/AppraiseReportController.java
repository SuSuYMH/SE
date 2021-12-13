package com.susu.se.controller.Report;

import com.susu.se.model.Class;
import com.susu.se.model.Experiment;
import com.susu.se.model.Report;
import com.susu.se.model.users.Teacher;
import com.susu.se.service.AppraiseReportService;
import com.susu.se.utils.Return.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reports")
public class AppraiseReportController {
    @Autowired
    private AppraiseReportService appraiseReportService;

    @PostMapping("/appraisebyta")
    public Result<String> appraiseByTA(@RequestParam("reportid") Integer reportId,@RequestParam("score") Double score,@RequestParam("pingyu") String pingYu, @RequestParam("assistantid") Integer assistantId){
        return appraiseReportService.appraiseReportByTA(reportId, score, pingYu, assistantId);
    }

    @PostMapping("/appraisebyte")
    public Result<String> appraiseByTeacher(@RequestParam("reportid") Integer reportId,@RequestParam("score") Double score,@RequestParam("pingyu") String pingYu, @RequestParam("teacherid") Integer teacherId){
        return appraiseReportService.appraiseReportByTeacher(reportId, score, pingYu, teacherId);
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

    @GetMapping("/getteacherofta/{assistantId}")
    public Result<Teacher> getTeacherByAssistantId(@PathVariable Integer assistantId){
        return appraiseReportService.getYourTeacher(assistantId);
    }
}
