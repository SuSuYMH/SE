package com.susu.se.repository;

import com.susu.se.model.Course;
import com.susu.se.model.CourseFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseFileRepository extends JpaRepository<CourseFile, Integer> {
    List<CourseFile> findByCourse(Course course);
    CourseFile findByFileName(String fileName);
}
