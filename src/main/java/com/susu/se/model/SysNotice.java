package com.susu.se.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.susu.se.model.users.Administrator;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "sys_notice")
public class SysNotice {
    @Id
    @Column(name = "notice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noticeId;

    private String title;

    private String content;

    private String category;

    private Date uploadTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name ="administrator_id")
    @JsonBackReference
    private Administrator administrator;
}
