package com.susu.se.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name ="report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;

    private String title;

    private String content;

    //这个实验报告的得分
    private String score;
}
