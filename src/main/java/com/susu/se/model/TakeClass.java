package com.susu.se.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.susu.se.model.users.Student;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "takes")
public class TakeClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer takesId;

    private Integer grade;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonBackReference
    private Student student;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    @JsonBackReference
    private Class kecheng;



}
