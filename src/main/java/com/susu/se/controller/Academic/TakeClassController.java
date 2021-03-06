package com.susu.se.controller.Academic;

import com.susu.se.model.Class;
import com.susu.se.model.users.Student;
import com.susu.se.service.TakeClassService;
import com.susu.se.utils.Return.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("takeclass")
public class TakeClassController {
    @Autowired
    private TakeClassService takeClassService;

    @GetMapping(path = "/byclassid/{classId}")
    public Result<List<Student>> getAllStudentByClassId(@PathVariable Integer classId){
        return takeClassService.getAllStudentByClassId(classId);
    }

    @GetMapping(path = "/bystudentid/{studentId}")
    public Result<List<Class>> getAllClassByStudentId(@PathVariable Integer studentId){
        return takeClassService.getAllClassByStudentId(studentId);
    }

    //参加某一个课程
    @RequiresPermissions("takeclass")
    @PostMapping(path = "/add")
    public Result<String> addTakesByStudentIdAndClassId(@RequestParam("classid") Integer classId, @RequestParam("studentid") Integer studentId){
        return takeClassService.takeClassByStudentIDAndClassID(studentId, classId);
    }

    @DeleteMapping(path = "/delete")
    public Result<String> deleteTakesByStudentIdAndClassId(@RequestParam("classid") Integer classId, @RequestParam("studentid") Integer studentId){
        return takeClassService.leaveClassByStudentIDAndClassID(studentId, classId);
    }

}
