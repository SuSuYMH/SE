package com.susu.se.repository;

import com.susu.se.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    //根据name查找user，这样可以找出数据库中已经加密过的密文密码
    User findUserByName(String name);
}
