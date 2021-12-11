package com.susu.se.repository;

import com.susu.se.model.Experiment;
import com.susu.se.model.Report;
import com.susu.se.model.users.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    Report findReportByExperimentAndStudent(Experiment experiment, Student student);
    List<Report> findReportsByExperiment(Experiment experiment);
}
