package com.susu.se.repository;

import com.susu.se.model.users.Teacher;
import com.susu.se.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Teacher findTeacherByUser(User user);
}
