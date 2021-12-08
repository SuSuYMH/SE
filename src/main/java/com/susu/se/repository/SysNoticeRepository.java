package com.susu.se.repository;

import com.susu.se.model.SysNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SysNoticeRepository extends JpaRepository<SysNotice, Integer> {
}
