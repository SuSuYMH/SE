package com.susu.se.repository;

import com.susu.se.model.users.Administrator;
import com.susu.se.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
    Administrator findAdministratorByUser(User user);
}
