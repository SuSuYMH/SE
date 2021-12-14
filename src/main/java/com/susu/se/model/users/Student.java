package com.susu.se.model.users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.susu.se.model.Report;
import com.susu.se.model.TakeClass;
import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name ="student")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer" ,"handler"})
@Proxy(lazy = false)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;

    private Integer grade;

    //与User的一对一,而且应该让这个user字段不能更新！
    //yysy用户表和学生表应该没有主从之分，删除其中一个就应该删除另外一个，但是如果双方都有对方主键的话会导致删不掉任意一个
    //所以我考虑的是删除的话就在对应的子表（继承关系的子）中删，然后级联删除到user表就得了
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    //一个学生参与多个班级
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonBackReference
    private List<TakeClass> takeClassList;

    //同一个学生会提交很多实验报告
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //外键字段的名称
    @JoinColumn(name = "student_id")
    private List<Report> reports;

}
