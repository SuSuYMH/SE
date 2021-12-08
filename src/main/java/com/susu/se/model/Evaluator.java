package com.susu.se.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "evaluator")
public class Evaluator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluator_id")
    private Integer evaluatorId;

    //1:责任教师
    //2:教师
    //3:助教
    private Integer category;

    private String name;

    private String password;

    private String email;

    //一对一关联
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "evaluator")
    //外键字段名
    @JoinColumn(name = "assistant_id")
    @JsonBackReference
    private Assistant assistant;

    //一对一关联
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "evaluator")
    //外键字段名
    @JoinColumn(name = "teacher_id")
    @JsonBackReference
    private Teacher teacher;

    //同一个评阅者会评阅很多实验报告
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //外键字段的名称
    @JoinColumn(name = "evaluator_id")
    private List<Report> reports;
}
