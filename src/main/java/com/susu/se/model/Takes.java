package com.susu.se.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "takes")
public class Takes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer takesId;

    private Integer grade;

}
