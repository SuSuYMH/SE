package com.susu.se.model;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name ="student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;

    private String name;

    private String password;

    private String email;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private List<Takes> takesList;

    //同一个学生会提交很多实验报告
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //外键字段的名称
    @JoinColumn(name = "student_id")
    private List<Report> reports;



}
