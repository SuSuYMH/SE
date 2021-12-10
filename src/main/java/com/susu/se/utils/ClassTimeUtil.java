package com.susu.se.utils;

import javax.xml.crypto.Data;

@lombok.Data
public class ClassTimeUtil {
    //星期几
    private Integer week;
    //上课时间
    private Data startTime;
    //下课时间
    private Data endTime;

    public ClassTimeUtil(Integer week, Data startTime, Data endTime) {
        this.week = week;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
