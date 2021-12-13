package com.susu.se.service;

import com.susu.se.model.Class;
import com.susu.se.model.Course;
import com.susu.se.model.Experiment;
import com.susu.se.model.Report;
import com.susu.se.model.users.Assistant;
import com.susu.se.model.users.Student;
import com.susu.se.model.users.Teacher;
import com.susu.se.repository.*;
import com.susu.se.utils.Return.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppraiseReportService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private AssistantRepository assistantRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private ExperimentService experimentService;

    @Autowired
    private ExperimentRepository experimentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    //评阅实验报告，给出报告id和分数即可
    //教师评阅
    public Result<String> appraiseReportByTeacher(Integer reportId, Double score, String pingYu, Integer teacherId){
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
    //助教评阅
    public Result<String> appraiseReportByTA(Integer reportId, Double score, String pingYu, Integer assistantId){
        Optional<Assistant> byId1 = assistantRepository.findById(assistantId);
        Assistant assistant = byId1.get();
        Optional<Report> byId = reportRepository.findById(reportId);
        Report report = byId.get();
        report.setScore(score);
        report.setPingYu(pingYu);
        report.setAppraiseTime(new Date());
        //1是老师 2是助教
        report.setTeacherOrAssistant(2);
        report.setAssistant(assistant);
        reportRepository.save(report);
        return Result.wrapSuccessfulResult("批改成功！");
    }

    //具体是这样的，老师打开评阅界面，列出一系列他的班级，点击某个班级可以看到班级所对应的课程的实验，点击实验，出现学生提交的实验报告
    //如果是助教，还要先查助教对应的老师
    //0.获取助教对应的老师
    public Result<Teacher> getYourTeacher(Integer assistantId){
        Optional<Assistant> byId = assistantRepository.findById(assistantId);
        Assistant assistant = byId.get();
        Teacher teacher = assistant.getTeacher();
        return Result.wrapSuccessfulResult(teacher);
    }
    //1.获取某一老师的所有班级
    public Result<List<Class>> getAllClassOfTeacher(Integer teacherId){
        Optional<Teacher> byId = teacherRepository.findById(teacherId);
        List<Class> classesByTeacher = classRepository.findClassesByTeacher(byId.get());
        return Result.wrapSuccessfulResult(classesByTeacher);
    }
    //2.获取这个班级对应的课程的实验(中间需要课程做中介)
    public Result<List<Experiment>> getAllExperimentOfClass(Integer classId){
        Optional<Class> byId = classRepository.findById(classId);
        Class BanJi = byId.get();
        //获取班级对应的课程
        Course course = BanJi.getCourse();
        //获取该课程的所有实验
        Result<List<Experiment>> allExperimentOfCourse = experimentService.getAllExperimentOfCourse(course.getCourse_id());
        return allExperimentOfCourse;
    }
    //3.获取这个实验的所有实验报告
    public Result<List<Report>> getAllReportOfExperiment(Integer experimentId){
        Optional<Experiment> byId = experimentRepository.findById(experimentId);
        Experiment experiment = byId.get();

        List<Report> reportsByExperiment = reportRepository.findReportsByExperiment(experiment);
        return Result.wrapSuccessfulResult(reportsByExperiment);
    }

    //获取某一个学生的所有实验报告
    public Result<List<Report>> getAllReportGrade(Integer studentId, Integer courseId){
        Optional<Course> byId = courseRepository.findById(courseId);
        Course course = byId.get();

        Optional<Student> byId1 = studentRepository.findById(studentId);
        Student student = byId1.get();

        List<Report> reports = new ArrayList<>();

        //获取该学生的所有实验报告
        Result<List<Experiment>> allExperimentOfCourseR = null;
        try {
            //获取某一课程的所有实验
            allExperimentOfCourseR = experimentService.getAllExperimentOfCourse(course.getCourse_id());
            List<Experiment> allExperimentOfCourse = allExperimentOfCourseR.getData();
            System.out.println(allExperimentOfCourse.size());
            //对每一个实验，通过学生和实验找出实验报告,获得这个学生关于这个班级的所有实验报告
            for(Experiment experiment:allExperimentOfCourse){
                Report reportByExperimentAndStudent = reportRepository.findReportByExperimentAndStudent(experiment, student);
                try {
                    reportByExperimentAndStudent.getAppraiseTime();
                    if(reportByExperimentAndStudent.getAppraiseTime()!=null){
                        reports.add(reportByExperimentAndStudent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("哪有实验！");
        }
        return Result.wrapSuccessfulResult(reports);
    }
}
