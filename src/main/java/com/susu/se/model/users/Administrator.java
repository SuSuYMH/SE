package com.susu.se.model.users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.susu.se.model.Course;
import com.susu.se.model.SysNotice;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "administrator")
@Proxy(lazy = false)
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "administrator_id")
    private Integer administerId;

    //与User的一对一
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

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
