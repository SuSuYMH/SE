package com.susu.se.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

@Data
@Entity
@Table(name = "class")
public class Class {
    @Id
    @Column(name = "class_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer classId;

    private String room;

    private Time startTime;

    private Time endTime;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private List<Takes> takesList;

//    @ManyToMany
//    //中间表需要JoinTable来维护外键
//    /*
//    * name 指定中间表的名称
//    * joinColumns 设置此中间表关于本表（class）的外键名称
//    * inverseJoinColumns 设置中间表关于另一个表（student）的外键名称
//    * */
//    @JoinTable(name = "takes",
//            joinColumns = {@JoinColumn(name ="class_id")},
//            inverseJoinColumns = {@JoinColumn(name = "student_id")}
//    )
//    private List<Student> students;

}
