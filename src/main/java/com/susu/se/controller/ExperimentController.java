package com.susu.se.controller;

import com.susu.se.model.Experiment;
import com.susu.se.service.ExperimentService;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("experiments")
public class ExperimentController {
    @Autowired
    private ExperimentService experimentService;

    //添加实验项目
    @PostMapping(path = "/add")
    public Result<String> addExperiment(@RequestParam("courseid") Integer courseId, @RequestParam("experimentname") String experimentName, @RequestParam("intro") String intro){
        return experimentService.addExperiment(courseId, experimentName, intro);
    }

    //获取所有实验项目
    @GetMapping
    public Result<List<Experiment>> getAll(){
        return experimentService.getAllExperiment();
    }

    //获取某一课程的所有实验项目
    @GetMapping("/{courseId}")
    public Result<List<Experiment>> getAllOfCourse(@PathVariable Integer courseId){
        return experimentService.getAllExperimentOfCourse(courseId);
    }

    //删除某一实验项目
    @DeleteMapping("/{experimentId}")
    public Result<String> deleteOneExperiment(@PathVariable Integer experimentId){
        return experimentService.deleteExperiment(experimentId);
    }

    //更新实验项目
    @PostMapping("/alter/{experimentid}")
    public Result<String> alterOneExperiment(@PathVariable Integer experimentid, @RequestParam("name") String experimentName, @RequestParam("intro") String intro){
        return experimentService.alterExperiment(experimentid, experimentName, intro);
    }
}
