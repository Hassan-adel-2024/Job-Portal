package com.jobportal.service;

import com.jobportal.entity.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserRoleService {
    List<UserRole> getAllUserRoles();
}
