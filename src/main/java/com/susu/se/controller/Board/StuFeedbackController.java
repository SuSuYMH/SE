package com.susu.se.controller.Board;

import com.susu.se.model.TakeClass;
import com.susu.se.service.Board.FeedbackService;
import com.susu.se.utils.Return.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class StuFeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public Result<List<TakeClass>> getAllFeedback(){
        return feedbackService.getAllTakeClassFeedback();
    }

    //其实知识添加字段，这可不是建表
    @RequiresPermissions("addfeedback")
    @PostMapping("/add")
    public Result<String> fillInFeedback(@RequestParam("studentid") Integer studentId,@RequestParam("classid") Integer classId,@RequestParam("feedback") String feedback){
        return feedbackService.writeInFeedback(studentId, classId, feedback);
    }
}
