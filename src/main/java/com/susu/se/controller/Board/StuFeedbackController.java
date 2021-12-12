package com.susu.se.controller.Board;

import com.susu.se.model.TakeClass;
import com.susu.se.service.Board.FeedbackService;
import com.susu.se.service.TakeClassService;
import com.susu.se.utils.Result;
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
    @PostMapping("/add")
    public Result<String> fillInFeedback(@RequestParam("studentid") Integer studentId,@RequestParam("classid") Integer classId,@RequestParam("feedback") String feedback){
        return feedbackService.writeInFeedback(studentId, classId, feedback);
    }
}
