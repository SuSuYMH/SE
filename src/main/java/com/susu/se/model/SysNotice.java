package com.susu.se.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name ="administrator_id")
//    private Administrator administrator;
}
