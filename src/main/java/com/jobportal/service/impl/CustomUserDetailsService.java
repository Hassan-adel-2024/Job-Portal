package com.jobportal.service.impl;

import com.jobportal.entity.User;
import com.jobportal.repository.UserRepo;
import com.jobportal.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Marks this class as a Spring service component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Step 1: Fetch the user from the database using the email (username)
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user with username: " + username));

        // Step 2: Wrap the user entity in a UserDetails implementation (CustomUserDetails)
        return new CustomUserDetails(user);
    }
}
