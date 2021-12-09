package com.susu.se.repository;

import com.susu.se.model.Course;
import com.susu.se.model.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperimentRepository extends JpaRepository<Experiment, Integer> {
    List<Experiment> findExperimentsByCourse(Course course);


}
