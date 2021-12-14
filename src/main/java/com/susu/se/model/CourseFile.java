package com.susu.se.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.susu.se.model.users.Teacher;
import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name ="coursefile")
@Proxy(lazy = false)
public class CourseFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseFileId;

    private String fileName;

    private String path;

    private Integer size;

    private String type;

    //下载次数
    private Integer downloadTimes;

    //上传时间
    private Date uploadTime;

    //一门课程对应多个课程文件,这个detach是解除关联，
    @ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    //外键字段的名称
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;

    //一个老师对应多个课程文件
    @ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    //外键字段的名称
    @JoinColumn(name = "teacher_id")
    @JsonBackReference
    private Teacher teacher;

}
