package com.jobportal.repository;

import com.jobportal.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepo extends JpaRepository<UserRole,Long> {
}
