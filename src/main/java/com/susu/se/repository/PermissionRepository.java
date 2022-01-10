package com.susu.se.repository;

import com.susu.se.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    List<Permission> findPermissionsByShouldBelongRoleID(Integer roleID);
}
