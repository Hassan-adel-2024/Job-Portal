package com.jobportal.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component // Marks this class as a Spring-managed component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // Step 1: Get the authenticated user's details
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        // Step 2: Log the successful login
        System.out.println("username is : " + username + " is logged in");

        // Step 3: Check the user's roles/authorities
        boolean hasJobSeeker = authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("job seeker"));
        boolean hasRecruiter = authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("recruiter"));

        // Step 4: Redirect based on the user's role
        if (hasJobSeeker || hasRecruiter) {
            response.sendRedirect("/dashboard"); // Send user to the dashboard
        }
    }
}

