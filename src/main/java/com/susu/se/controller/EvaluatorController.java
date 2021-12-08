package com.susu.se.controller;

import com.susu.se.model.Evaluator;
import com.susu.se.model.SysNotice;
import com.susu.se.service.EvaluatorService;
import com.susu.se.service.SysNoticeService;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("evaluators")
public class EvaluatorController {
    @Autowired
    private EvaluatorService evaluatorService;

    //查询单个sysnotice
    @GetMapping(path="/{id}")
    public Result<Evaluator> getById(@PathVariable Integer id){
        return evaluatorService.getById(id);
    }

    //查询所有nnotice
    @GetMapping
    public Result<List<Evaluator>> getAll(){
        return evaluatorService.getAll();
    }
}
