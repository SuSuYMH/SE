package com.susu.se.service;

import com.susu.se.model.Class;
import com.susu.se.model.Course;
import com.susu.se.model.Experiment;
import com.susu.se.model.Report;
import com.susu.se.model.users.Teacher;
import com.susu.se.repository.ClassRepository;
import com.susu.se.repository.ExperimentRepository;
import com.susu.se.repository.ReportRepository;
import com.susu.se.repository.TeacherRepository;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppraiseReportService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private ExperimentService experimentService;

    @Autowired
    private ExperimentRepository experimentRepository;

    //评阅实验报告，给出报告id和分数即可
    public Result<String> appraiseReport(Integer reportId, Integer score, String pingYu, Integer teacherId){
        Optional<Teacher> byId1 = teacherRepository.findById(teacherId);
        Teacher teacher = byId1.get();
        Optional<Report> byId = reportRepository.findById(reportId);
        Report report = byId.get();
        report.setScore(score);
        report.setPingYu(pingYu);
        report.setAppraiseTime(new Date());
        //1是老师 2是助教
        report.setTeacherOrAssistant(1);
        report.setTeacher(teacher);
        reportRepository.save(report);
        return Result.wrapSuccessfulResult("批改成功！");
    }

    //具体是这样的，老师打开评阅界面，列出一系列他的班级，点击某个班级可以看到班级所对应的课程的实验，点击实验，出现学生提交的实验报告
    //获取某一老师的所有班级
    public Result<List<Class>> getAllClassOfTeacher(Integer teacherId){
        Optional<Teacher> byId = teacherRepository.findById(teacherId);
        List<Class> classesByTeacher = classRepository.findClassesByTeacher(byId.get());
        return Result.wrapSuccessfulResult(classesByTeacher);
    }
    //获取这个班级对应的课程的实验(中间需要课程做中介)
    public Result<List<Experiment>> getAllExperimentOfClass(Integer classId){
        Optional<Class> byId = classRepository.findById(classId);
        Class BanJi = byId.get();
        //获取班级对应的课程
        Course course = BanJi.getCourse();
        //获取该课程的所有实验
        Result<List<Experiment>> allExperimentOfCourse = experimentService.getAllExperimentOfCourse(course.getCourse_id());
        return allExperimentOfCourse;
    }
    //获取这个实验的所有实验报告
    public Result<List<Report>> getAllReportOfExperiment(Integer experimentId){
        Optional<Experiment> byId = experimentRepository.findById(experimentId);
        Experiment experiment = byId.get();

        List<Report> reportsByExperiment = reportRepository.findReportsByExperiment(experiment);
        return Result.wrapSuccessfulResult(reportsByExperiment);
    }
}
