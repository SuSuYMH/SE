package com.susu.se.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Data
@Entity
@Table(name ="teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teacherId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluator_id")
    @JsonBackReference
    private Evaluator evaluator;

    //一个老师对应多个助教，删除老师的同时删除助教，没有老师助教没有意义
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private List<Assistant> assistants;

    //老师向某个课程上传文件,删除老师不会删除对应的文件
    @OneToMany(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private List<CourseFile> courseFiles;
}
