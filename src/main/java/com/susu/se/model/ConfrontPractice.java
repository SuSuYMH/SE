package com.susu.se.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "confrontpractice")
public class ConfrontPractice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cpId;

    //对抗练习得分
    private Double score;

    //创建时间
    private Date uploadTime;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "takes_id")
    @JsonBackReference
    private TakeClass takeClass;
}
