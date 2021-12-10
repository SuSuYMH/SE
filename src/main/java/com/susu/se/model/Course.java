package com.susu.se.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer course_id;

    private String name;

    //课程介绍
    private String intro;

    //一对多的时候默认是懒加载
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //外键字段的名称
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private List<Class> classes;

    //一对多的时候默认是懒加载
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //外键字段的名称
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private List<Experiment> experiments;

    //一门课程对应多个课程文件
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //外键字段的名称
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private List<CourseFile> courseFiles;

    //一个管理员可以创建多个实验
    @ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    //外键字段的名称
    @JoinColumn(name = "administrator_id")
    @JsonBackReference
    private Administrator administrator;

}
