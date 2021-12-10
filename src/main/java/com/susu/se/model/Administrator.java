package com.susu.se.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "administrator")
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "administrator_id")
    private Integer administerId;

    private String name;

    private String password;

    private String email;

    private String salt;

    //一对多的时候默认是懒加载
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //外键字段的名称
    @JoinColumn(name = "administrator_id")
    @JsonBackReference
    private List<SysNotice> sysNotices;

    //一对多的时候默认是懒加载
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //外键字段的名称
    @JoinColumn(name = "administrator_id")
    private List<Course> courses;
}
