package com.jobportal.service.impl;

import com.jobportal.entity.UserRole;
import com.jobportal.repository.UserRoleRepo;
import com.jobportal.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private UserRoleRepo userRoleRepo;

    public UserRoleServiceImpl(UserRoleRepo userRoleRepo) {
        this.userRoleRepo = userRoleRepo;
    }
    public List<UserRole> getAllUserRoles(){
        return userRoleRepo.findAll();
    }
}
