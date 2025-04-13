package com.jobportal.controller;

import com.jobportal.entity.RecruiterProfile;
import com.jobportal.entity.User;
import com.jobportal.service.impl.RecruiterProfileServiceImpl;
import com.jobportal.service.impl.UserServiceImpl;
import com.jobportal.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private final UserServiceImpl userService;
    private final RecruiterProfileServiceImpl recruiterProfileService;

    @Autowired
    public RecruiterProfileController(UserServiceImpl userService, RecruiterProfileServiceImpl recruiterProfileService) {
        this.userService = userService;
        this.recruiterProfileService = recruiterProfileService;
    }

    @GetMapping("/")
    public String recruiterProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String username = auth.getName();
            User user = userService.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Could not find user with this email"));
            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.findRecruiterProfileById(user.getUserId());
            recruiterProfile.ifPresent(profile -> model.addAttribute("profile", profile));
        }
        return "recruiter-profile";
    }

    @GetMapping("/addNew")
    public String showProfileForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String username = auth.getName();
            User user = userService.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Could not find user with this email"));
            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.findRecruiterProfileById(user.getUserId());
            model.addAttribute("profile", recruiterProfile.orElse(new RecruiterProfile()));
        }
        return "recruiter-profile";
    }

    @PostMapping("/addNew")
    public String addNew(RecruiterProfile recruiterProfile,
                         @RequestParam("image") MultipartFile multipartFile, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String username = auth.getName();
            User user = userService.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Could not find user with this email"));
            recruiterProfile.setUser(user);
            recruiterProfile.setUserAccountId(user.getUserId());
        }
        model.addAttribute("profile", recruiterProfile);

        String fileName = "";
        if (!multipartFile.isEmpty()) {
            fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            recruiterProfile.setProfilePhotoUrl(fileName);
        }

        RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);

        String uploadDir = "photos/recruiter/" + savedUser.getUserAccountId();
        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/dashboard";
    }
}
