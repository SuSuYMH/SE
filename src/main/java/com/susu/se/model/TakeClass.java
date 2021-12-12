package com.susu.se.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.susu.se.model.users.Student;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "takes")
public class TakeClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer takesId;

    private Integer grade;

    //参与课程的学生才能有反馈信息，其实可以新建一张表出来，但还是放在这里得了
    private String feedback;

    private Date feedbackTime;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonBackReference
    private Student student;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    @JsonBackReference
    private Class kecheng;



}
