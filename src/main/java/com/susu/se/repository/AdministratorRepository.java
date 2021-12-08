package com.susu.se.repository;

import com.susu.se.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
    //根据name查找administrater，这样可以找出数据库中已经加密过的密文密码
    Administrator findByName(String name);
}
