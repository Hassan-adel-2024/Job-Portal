package com.jobportal.controller;

import com.jobportal.exception.EmailAlreadyExists;
import com.jobportal.entity.User;
import com.jobportal.entity.UserRole;
import com.jobportal.service.impl.UserRoleServiceImpl;
import com.jobportal.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    private final UserServiceImpl userService;
    private final UserRoleServiceImpl userRoleService;

    @Autowired
    public UserController(UserServiceImpl userService, UserRoleServiceImpl userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }


    @GetMapping("/register")
    public String register(Model model) {
        List<UserRole> userRoleList = userRoleService.getAllUserRoles();
        model.addAttribute("userRoles", userRoleList);
        model.addAttribute("user", new User()); //
        return "register";

    }

    @PostMapping("/register/new")
    public String saveUserRegistration(@Valid @ModelAttribute("user") User user, Model model, BindingResult result) {
        Long roleId = user.getUserRole().getRoleId();
        try {
            userService.addUser(user, roleId);
        } catch (EmailAlreadyExists e) {
            result.rejectValue("email", null, e.getMessage());
            List<UserRole> userRoleList = userRoleService.getAllUserRoles();
            model.addAttribute("userRoles", userRoleList);
            return "register";
        }

        System.out.println(user);
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

}
