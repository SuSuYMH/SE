//package com.susu.se.model;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import lombok.Data;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Data
//@Entity
//@Table(name = "attendance")
//public class Attendance {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer attendanceId;
//
//    //到了就是true，没到就是false
//    private Boolean attendOrNot;
//
//    //创建签到时间
//    private Date uploadTime;
//
//    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
//    @JoinColumn(name = "takes_id")
//    @JsonBackReference
//    private TakeClass takeClass;
//}
