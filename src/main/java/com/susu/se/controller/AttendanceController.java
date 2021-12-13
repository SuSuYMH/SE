package com.susu.se.controller;

import com.susu.se.service.AttendanceService;
import com.susu.se.utils.Return.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("attendances")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    //设置签到
    @PostMapping("/set")
    public Result<String> set(@RequestParam("classid") Integer classId,@RequestParam("code") String attendCode){
        return attendanceService.addAttendanceToAllStudentOfClass(classId, attendCode);
    }

    //学生签到
    @PostMapping("/attend")
    public Result<String> attend(@RequestParam("studentid") Integer studentId, @RequestParam("classid") Integer classId,@RequestParam("code") String attendCode){
        return attendanceService.studentAttendClass(studentId, classId, attendCode);
    }
}
