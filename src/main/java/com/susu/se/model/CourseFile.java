package com.susu.se.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name ="coursefile")
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

}
