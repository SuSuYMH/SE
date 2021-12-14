package com.susu.se.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.susu.se.model.users.Student;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "takes")
@Proxy(lazy = false)
public class TakeClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer takesId;

    private Integer grade;

    //参与课程的学生才能有反馈信息，其实可以新建一张表出来，但还是放在这里得了
    private String feedback;

    //填写反馈信息的时间
    private Date feedbackTime;

    //总出勤次数，学生签到成功一次就加一
    private Double numOfAttendance;

    //需要出勤的总次数，老师设置一次签到就加一
    private  Double AllNumOfAttendance;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonBackReference
    private Student student;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    @JsonBackReference
    private Class kecheng;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "takes_id")
//    @JsonBackReference
//    private List<Attendance> attendances;



}
