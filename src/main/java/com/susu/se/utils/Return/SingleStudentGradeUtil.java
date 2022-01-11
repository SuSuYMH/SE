package com.susu.se.utils.Return;

import lombok.Data;

@Data
public class SingleStudentGradeUtil {
    private String name;
    //学生ID
    private Integer studentID;
    private String className;
    //总成绩
    private Double grade;

    //报告所占比例
    private int reportRate;
    //签到所占比例
    private int attendRate;
    //报告成绩
    private Double reportScore;
    //签到成绩
    private Double attendScore;

}