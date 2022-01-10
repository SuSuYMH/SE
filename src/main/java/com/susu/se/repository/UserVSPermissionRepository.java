package com.susu.se.repository;

import com.susu.se.model.Permission;
import com.susu.se.model.UserVSPermission;
import com.susu.se.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserVSPermissionRepository extends JpaRepository<UserVSPermission, Integer> {
    List<UserVSPermission> findUserVSPermissionsByUser(User user);
    List<UserVSPermission> findUserVSPermissionsByUserAndPermission(User user, Permission permission);
}
