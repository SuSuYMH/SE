package com.susu.se.controller.Report;

import com.susu.se.model.Report;
import com.susu.se.service.SubmitReportService;
import com.susu.se.utils.Return.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reports")
public class SubmitReportController {
    @Autowired
    private SubmitReportService submitReportService;

    @RequiresPermissions("submitReport")
    @PostMapping(path = "/submit")
    public Result<String> submit(@RequestParam("experimentid") Integer experimentId,@RequestParam("studentid") Integer studentId,@RequestParam("title") String title,@RequestParam("content") String content,@RequestParam("conclusion") String conclusion){
        return submitReportService.submitReport(experimentId, studentId, title, content, conclusion);
    }

    @RequiresPermissions("alterReport")
    @PostMapping(path = "/alter")
    public Result<String> alter(@RequestParam("reportid") Integer reportId,@RequestParam("title") String title,@RequestParam("content") String content,@RequestParam("conclusion") String conclusion){
        return submitReportService.alterReport(reportId, title, content, conclusion);
    }


    //这里有一个问题就是，一定experimentid和studentid只对应一份报告！！！！！所以前端，第一次提交实验报告时是创建，之后都是修改！！！
    @GetMapping(path = "/get")
    public Result<Report> get(@RequestParam("experimentid") Integer experimentId,@RequestParam("studentid") Integer studentId){
        return submitReportService.getReportByExperimentIdAndStudentId(experimentId, studentId);
    }
}
