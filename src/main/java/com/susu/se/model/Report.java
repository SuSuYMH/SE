package com.susu.se.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.susu.se.model.users.Assistant;
import com.susu.se.model.users.Student;
import com.susu.se.model.users.Teacher;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Data
@Entity
@Table(name ="report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;

    //实验报告标题
    private String title;

    //实验报告主体内容
    private String content;

    //实验报告结论
    private String conclusion;

    //是否完成
    private Boolean endOrNot;

    //这个实验报告的得分
    private Double score;

    //老师打得评语
    private String pingYu;

    //评阅时间
    private Date appraiseTime;

    //上传时间
    private Date submitTime;

    //更新时间
    private Date updateTime;

    //实验报告得分是由教师给出，还是由助教给出
    //1-教师
    //2-助教
    private Integer TeacherOrAssistant;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonBackReference
    private Student student;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    @JsonBackReference
    private Teacher teacher;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "assistant_id")
    @JsonBackReference
    private Assistant assistant;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "experiment_id")
    @JsonBackReference
    private Experiment experiment;
}
