package com.jobportal.service.impl;

import com.jobportal.entity.JobSeekerProfile;
import com.jobportal.entity.RecruiterProfile;
import com.jobportal.exception.EmailAlreadyExists;
import com.jobportal.entity.User;
import com.jobportal.entity.UserRole;
import com.jobportal.repository.JobSeekerProfileRepo;
import com.jobportal.repository.RecruiterProfileRepo;
import com.jobportal.repository.UserRepo;
import com.jobportal.repository.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserServiceImpl {
    private final UserRepo userRepo;
    private final UserRoleRepo userRoleRepo;
    private final RecruiterProfileRepo recruiterProfileRepo;
    private final JobSeekerProfileRepo jobSeekerProfileRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, UserRoleRepo userRoleRepo,
                           RecruiterProfileRepo recruiterProfileRepo,
                           JobSeekerProfileRepo jobSeekerProfileRepo,
                           PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userRoleRepo = userRoleRepo;
        this.recruiterProfileRepo = recruiterProfileRepo;
        this.jobSeekerProfileRepo = jobSeekerProfileRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Adds a new user and assigns a role.
     *
     * @param user   The user to be added.
     * @param roleId The ID of the role to assign to the user.
     * @return The saved user entity.
     */
    public Optional<User> findByEmail(String email){
        return userRepo.findByEmail(email);
    }
    public User addUser(User user, Long roleId) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyExists("Another user already exists with this email");
        }

        user.setActive(true);
        user.setRegistrationDate(LocalDate.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assign role
        UserRole role = userRoleRepo.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found with ID: " + roleId));
        user.setUserRole(role);
        User savedUser = userRepo.save(user);
        if (roleId == 1) {
            recruiterProfileRepo.save(new RecruiterProfile(user));
        } else {
            jobSeekerProfileRepo.save(new JobSeekerProfile(user));
        }

        return savedUser;
    }

    public Object getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            User user = userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
            Long userId = user.getUserId();
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("recruiter"))) {
                RecruiterProfile recruiterProfile = recruiterProfileRepo.findById(userId).orElse(new RecruiterProfile());
                return recruiterProfile;
            } else {
                JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepo.findById(userId).orElse(new JobSeekerProfile());
                return jobSeekerProfile;
            }
        }
        return null;
    }
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)){
            String username = auth.getName();
            User user = userRepo.findByEmail(username).
                    orElseThrow(()->new UsernameNotFoundException("Could not found user with that username"));
            return user;
        }
        return null;
    }
}


