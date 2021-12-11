package com.susu.se.service;

import com.susu.se.model.Experiment;
import com.susu.se.model.Report;
import com.susu.se.model.users.Student;
import com.susu.se.repository.ExperimentRepository;
import com.susu.se.repository.ReportRepository;
import com.susu.se.repository.StudentRepository;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class SubmitReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ExperimentRepository experimentRepository;

    //提交实验，根据experimentId和studentId
    public Result<String> submitReport(Integer experimentId, Integer studentId, String title, String content, String conclusion){
        Report report = new Report();
        report.setTitle(title);
        report.setContent(content);
        report.setConclusion(conclusion);

        Optional<Student> byId = studentRepository.findById(studentId);
        Student student = byId.get();
        Optional<Experiment> byId1 = experimentRepository.findById(experimentId);
        Experiment experiment = byId1.get();

        report.setStudent(student);
        report.setExperiment(experiment);
        report.setSubmitTime(new Date());
        report.setEndOrNot(Boolean.TRUE);

        reportRepository.save(report);
        return Result.wrapSuccessfulResult("成功提交实验报告！");
    }

    //修改实验报告
    public Result<String> alterReport(Integer reportId, String title, String content, String conclusion){
        Optional<Report> reportOP = reportRepository.findById(reportId);
        Report report = reportOP.get();
        report.setTitle(title);
        report.setContent(content);
        report.setConclusion(conclusion);

        report.setUpdateTime(new Date());

        reportRepository.save(report);
        return Result.wrapSuccessfulResult("实验报告修改成功！");
    }

    //根据experimentId和studentId查看实验报告
    public Result<Report> getReportByExperimentIdAndStudentId(Integer experimentId, Integer studentId){
        Optional<Student> byId = studentRepository.findById(studentId);
        Student student = byId.get();
        Optional<Experiment> byId1 = experimentRepository.findById(experimentId);
        Experiment experiment = byId1.get();
        Report reportByExperimentAndStudent = reportRepository.findReportByExperimentAndStudent(experiment, student);
        return Result.wrapSuccessfulResult(reportByExperimentAndStudent);
    }

}
