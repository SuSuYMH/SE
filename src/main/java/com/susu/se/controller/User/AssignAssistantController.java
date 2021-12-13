package com.susu.se.controller.User;

import com.susu.se.service.AssistantService;
import com.susu.se.utils.Return.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assignta")
public class AssignAssistantController {
    @Autowired
    private AssistantService assistantService;

    @PostMapping
    public Result<String> assign(@RequestParam("teacherid") Integer teacherId,@RequestParam("assistantid") Integer assistantId){
        return assistantService.chooseTA(teacherId, assistantId);
    }
}
