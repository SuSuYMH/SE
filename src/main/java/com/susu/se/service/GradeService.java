package com.susu.se.service;

import com.susu.se.model.Class;
import com.susu.se.model.Experiment;
import com.susu.se.model.Report;
import com.susu.se.model.TakeClass;
import com.susu.se.model.users.Student;
import com.susu.se.repository.ClassRepository;
import com.susu.se.repository.ReportRepository;
import com.susu.se.repository.StudentRepository;
import com.susu.se.repository.TakeClassRepository;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GradeService {
    //自动生成当前学生这门课程的成绩
    @Autowired
    private TakeClassRepository takeClassRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ExperimentService experimentService;

    @Autowired
    private ReportRepository reportRepository;


    public Result<Double> getGradeByStudentIdAndClassId(Integer studentId, Integer classId){
        //获取成绩比例
        Optional<Student> STUDENTbyId = studentRepository.findById(studentId);
        Student student = STUDENTbyId.get();
        Optional<Class> BANJIbyId = classRepository.findById(classId);
        Class BanJi = BANJIbyId.get();
        Integer Rrate = BanJi.getReportRate();
        Integer Arate = BanJi.getAttendRate();
        Integer CPrate = BanJi.getConfrontPracticeRate();

        //获取签到成绩
        TakeClass kecheng = takeClassRepository.findTakeClassByStudentAndKecheng(student, BanJi);
        Double A_Grade = 0.0;
        if(kecheng.getNumOfAttendance()!=0){
            //签过到才到这里来，不然0/0的结果是NAN
            try {
                A_Grade = kecheng.getNumOfAttendance()/kecheng.getAllNumOfAttendance()*Arate;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("老师没发过签到");
            }
            System.out.println(A_Grade);
        }


        //获取报告成绩
        Double R_Grade = 0D;
        Result<List<Experiment>> allExperimentOfCourseR = null;
        try {
            //获取某一班级的所有实验
            allExperimentOfCourseR = experimentService.getAllExperimentOfCourse(BanJi.getCourse().getCourse_id());
            List<Experiment> allExperimentOfCourse = allExperimentOfCourseR.getData();
            System.out.println(allExperimentOfCourse.size());
            System.out.println("_____________________");
            //对每一个实验，通过学生和实验找出实验报告,获得这个学生关于这个班级的所有实验报告
            List<Report> reports = new ArrayList<>();
            for(Experiment experiment:allExperimentOfCourse){
                try {
                    //如果对应的实验，这个学生有参加的话
                    System.out.println("////");
                    System.out.println("现在的情况是这样，找不到的时候它居然不抛异常？？？？？？他会求出allExperimentOfCourse.size()个report，不管是否进行了创建，所以我怀疑是，JPA在创建了experiment和相应的student后就自动根据关系创建了report！！！我干！");
                    reportRepository.findReportByExperimentAndStudent(experiment, student);
                } catch (Exception e) {
                    //没有参加就继续查下一个
                    System.out.println("没参加吼吼吼～～～");
                    continue;
                }
                Report reportByExperimentAndStudent = reportRepository.findReportByExperimentAndStudent(experiment, student);
                //想要再详细研究用下面这两行！！！这肯定有bug！！！
//                System.out.println(reportByExperimentAndStudent.getStudent().getStudentId());
//                System.out.println("studentid");
                reports.add(reportByExperimentAndStudent);
            }
            System.out.println(reports.size());
            //求成绩
            for(Report report:reports){
                //对于老师已经评阅过的作业
                try {
                    if(report.getAppraiseTime()!=null){
                        R_Grade = R_Grade + report.getScore()/100;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("没参加吼吼吼～～～");
                }
            }
            R_Grade = R_Grade*Rrate;
            System.out.println(R_Grade);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("哪有实验！");
        }


        //求对抗练习成绩
        Integer CP_Grade = CPrate;

        return Result.wrapSuccessfulResult(A_Grade+R_Grade+CP_Grade);
    }
}
