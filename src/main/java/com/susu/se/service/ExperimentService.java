package com.susu.se.service;

import com.susu.se.model.Course;
import com.susu.se.model.Experiment;
import com.susu.se.repository.CourseRepository;
import com.susu.se.repository.ExperimentRepository;
import com.susu.se.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExperimentService {
    @Autowired
    private ExperimentRepository experimentRepository;

    @Autowired
    private CourseRepository courseRepository;

    //给定课程ID，给这个课程添加实验。是谁添加的这个实验无所谓，这里不进行记录
    public Result<String> addExperiment(Integer courseId, String experimentName, String intro){
        //根据课程id获取课程实体对象
        Optional<Course> byId = courseRepository.findById(courseId);
        Course course = byId.get();
        Experiment experiment = new Experiment();
        experiment.setIntro(intro);
        experiment.setName(experimentName);
        experiment.setCourse(course);
        experimentRepository.save(experiment);
        return Result.wrapSuccessfulResult("实验创建成功！");
    }

    //获取所有实验
    public Result<List<Experiment>> getAllExperiment(){
        List<Experiment> experimentslist = (List<Experiment>) experimentRepository.findAll();
        return new Result().wrapSuccessfulResult(experimentslist);
    }

    //获取某一课程的所有实验
    public Result<List<Experiment>> getAllExperimentOfCourse(Integer courseId){
        Optional<Course> byId = courseRepository.findById(courseId);
        List<Experiment> experimentsByCourse = experimentRepository.findExperimentsByCourse(byId.get());
        return new Result().wrapSuccessfulResult(experimentsByCourse);
    }

    //删除某一课程
    public Result<String> deleteExperiment(Integer experimentId){
        experimentRepository.deleteById(experimentId);
        return Result.wrapSuccessfulResult("删除成功！");
    }

    //修改某一课程
    public Result<String> alterExperiment(Integer experimentId, String experimentName,String intro){
        Experiment experiment = experimentRepository.getById(experimentId);
        experiment.setName(experimentName);
        experiment.setIntro(intro);
        experimentRepository.save(experiment);
        return Result.wrapSuccessfulResult("更改成功！");
    }
}
