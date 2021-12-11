package com.susu.se.model.users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.susu.se.model.Class;
import com.susu.se.model.CourseFile;
import com.susu.se.model.Report;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name ="teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teacherId;

    //与User的一对一
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    //一个老师对应多个助教，删除老师的同时删除助教，没有老师助教没有意义
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private List<Assistant> assistants;

    //老师向某个课程上传文件,删除老师不会删除对应的文件
    @OneToMany(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private List<CourseFile> courseFiles;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    @JsonBackReference
    private List<Class> classes;

    //同一个评阅者会评阅很多实验报告
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //外键字段的名称
    @JoinColumn(name = "teacher_id")
    private List<Report> reports;


}
