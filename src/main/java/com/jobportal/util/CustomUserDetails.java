package com.jobportal.util;

import com.jobportal.entity.User;
import com.jobportal.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// Custom implementation of UserDetails to integrate your User entity with Spring Security
public class CustomUserDetails implements UserDetails {

    private final User user;

    // Constructor to initialize the CustomUserDetails object with a User entity
    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Step 1: Get the user's role and map it to Spring Security authorities
        UserRole userRole = user.getUserRole();
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(userRole.getRoleName()));
        return authorityList;
    }

    @Override
    public String getPassword() {
        // Step 2: Return the user’s password (for authentication)
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // Step 3: Return the user’s email as the username
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        // Step 4: Indicates whether the user's account has expired (true = active)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Step 5: Indicates whether the user is locked out (true = not locked)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Step 6: Indicates whether the user's credentials (password) are expired
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Step 7: Indicates whether the user's account is enabled
        return true;
    }
}

