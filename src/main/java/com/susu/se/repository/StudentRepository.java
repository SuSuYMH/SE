package com.susu.se.repository;

import com.susu.se.model.users.Student;
import com.susu.se.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findStudentByUser(User user);
}
