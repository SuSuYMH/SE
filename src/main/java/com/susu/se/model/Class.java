package com.susu.se.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.susu.se.model.users.Teacher;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "class")
////在实体类中 发现有字段为null，在转化成json的时候，fasterxml.jackson将对象转换为json报错
////解决办法在实体类上面添加：
@JsonIgnoreProperties(value = {"hibernateLazyInitializer" ,"handler"})
public class Class {
    @Id
    @Column(name = "class_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer classId;

    private String room;

    //经过慎重的考虑，还是一个星期一节课就得了，要是有多个时间点上课的话，就要创建一个上课时间的表，而且与class表还是多对多的关系，就还要搞出
    //一个中间表，太麻烦太麻烦！

    //星期几
    private Integer week;
    //上课时间
    private String startTime;
    //下课时间
    private String endTime;

    //成绩比例
    //报告占比
    private Integer reportRate;
    //签到占比
    private Integer attendRate;
    //对抗练习占比
    private Integer ConfrontPracticeRate;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    @JsonBackReference
    private List<TakeClass> takeClassList;

    //一个课程对应多个班级公告
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    @JsonBackReference
    private List<ClassNotice> classNotices;

    //一个课程可以有多个班级
    @ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    //外键字段的名称
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;

    //一个课程对应一个老师，删除课程不影响老师
    @ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    @JsonBackReference
    private Teacher teacher;

}
