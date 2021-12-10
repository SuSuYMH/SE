package com.susu.se.repository;

import com.susu.se.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import com.susu.se.model.Class;

import java.util.List;

public interface ClassRepository extends JpaRepository<Class, Integer> {
    List<Class> findClassesByCourse(Course course);
}
