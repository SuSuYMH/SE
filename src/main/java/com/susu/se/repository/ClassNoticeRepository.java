package com.susu.se.repository;

import com.susu.se.model.Class;
import com.susu.se.model.ClassNotice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassNoticeRepository extends JpaRepository<ClassNotice, Integer> {
    List<ClassNotice> findClassNoticesByKecheng(Class aClass);
}
