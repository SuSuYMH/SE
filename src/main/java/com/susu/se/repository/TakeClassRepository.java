package com.susu.se.repository;

import com.susu.se.model.Class;
import com.susu.se.model.TakeClass;
import com.susu.se.model.users.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TakeClassRepository extends JpaRepository<TakeClass, Integer> {
    TakeClass findTakeClassByStudentAndKecheng(Student student, Class aClass);
    List<TakeClass> findTakeClassesByStudent(Student student);
    List<TakeClass> findTakeClassesByKecheng(Class aClsss);
}
