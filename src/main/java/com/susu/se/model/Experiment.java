package com.susu.se.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "experiment")
public class Experiment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "experiment_id")
    private Integer experimentId;

    private String name;

    private String intro;

    //同一个实验会有很多实验报告
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //外键字段的名称
    @JoinColumn(name = "experiment_id")
    private List<Report> reports;

    //多个实验对应一个课程，删除实验时不删除课程，所以是detach
    @ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    //外键字段的名称
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;

}
