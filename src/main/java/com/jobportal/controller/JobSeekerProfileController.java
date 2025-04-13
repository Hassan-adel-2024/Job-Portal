package com.jobportal.controller;

import com.jobportal.entity.JobSeekerProfile;
import com.jobportal.entity.Skill;
import com.jobportal.entity.User;
import com.jobportal.exception.ResourceNotFound;
import com.jobportal.service.impl.JobSeekerProfileService;
import com.jobportal.service.impl.UserServiceImpl;
import com.jobportal.util.FileUploadUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
@RequestMapping("/job-seeker-profile")
public class JobSeekerProfileController {

    @Value("${file.upload-dir}")
    private String uploadDirectory;
    private final UserServiceImpl userService;
    private final JobSeekerProfileService jobSeekerProfileService;

    @Autowired
    public JobSeekerProfileController(UserServiceImpl userService,
                                      JobSeekerProfileService jobSeekerProfileService) {
        this.userService = userService;
        this.jobSeekerProfileService = jobSeekerProfileService;
    }

    @PreAuthorize("hasAuthority('job seeker')")
    @GetMapping("/")
    public String viewProfile(Model model) {
        User currentUser = getAuthenticatedUser();
        JobSeekerProfile profile = jobSeekerProfileService.findJobSeekerProfileById(currentUser.getUserId())
                .orElseGet(() -> new JobSeekerProfile(currentUser));

        if (profile.getSkills().isEmpty()) {
            profile.getSkills().add(new Skill());
        }

        model.addAttribute("profile", profile);
        return "job-seeker-profile";
    }

    @PreAuthorize("hasAuthority('job seeker')")
    @PostMapping("/addNew")
    public String saveProfile(@ModelAttribute("profile") @Valid JobSeekerProfile profile,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "job-seeker-profile";
        }

        User currentUser = getAuthenticatedUser();

        // 🔍 Check if profile already exists
        JobSeekerProfile existingProfile = jobSeekerProfileService.findJobSeekerProfileById(currentUser.getUserId())
                .orElse(null);

        if (existingProfile != null) {
            // ❗ Update existing profile instead of inserting a new one
            existingProfile.setFirstName(profile.getFirstName());
            existingProfile.setLastName(profile.getLastName());
            existingProfile.setMajor(profile.getMajor());
            existingProfile.setUniversity(profile.getUniversity());
            existingProfile.setGraduationDate(profile.getGraduationDate());
            existingProfile.setCountry(profile.getCountry());
            existingProfile.setCity(profile.getCity());
            existingProfile.setCurrentJob(profile.getCurrentJob());
            existingProfile.setPhotosImagePath(profile.getPhotosImagePath());
            existingProfile.setResume(profile.getResume());

            jobSeekerProfileService.saveOrUpdateJobSeekerProfile(existingProfile);
        } else {
            // 🆕 Create a new profile if none exists
            profile.setUser(currentUser);
            jobSeekerProfileService.saveOrUpdateJobSeekerProfile(profile);
        }

        redirectAttributes.addFlashAttribute("message", "Profile saved successfully!");
        return "redirect:/dashboard";
    }


    @PreAuthorize("hasAuthority('job seeker')")
    @PostMapping("/upload-photo")
    public String uploadPhoto(@RequestParam("photo") MultipartFile file,
                              RedirectAttributes redirectAttributes) {
        JobSeekerProfile profile = new JobSeekerProfile();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String username = auth.getName();
            User user = userService.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
            profile = jobSeekerProfileService.
                    findJobSeekerProfileById(user.getUserId()).orElseThrow(() -> new ResourceNotFound("profile not found"));

        }
        String imageName = "";
        if(!file.isEmpty()){
            imageName= StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            profile.setPhotosImagePath(imageName);

        }
        JobSeekerProfile savedProfile = jobSeekerProfileService.saveOrUpdateJobSeekerProfile(profile);
        String uploadDir = "photos/candidates/" + savedProfile.getJobSeekerProfileId();
        try {
            FileUploadUtil.saveFile(uploadDir,imageName,file);
        }catch (Exception e){
            e.printStackTrace();
        }

            return "redirect:/dashboard";
    }

    @PreAuthorize("hasAuthority('job seeker')")
    @PostMapping("/upload-resume")
    public String uploadResume(@RequestParam("resume") MultipartFile file,
                              RedirectAttributes redirectAttributes) {
        JobSeekerProfile profile = new JobSeekerProfile();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String username = auth.getName();
            User user = userService.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
            profile = jobSeekerProfileService.
                    findJobSeekerProfileById(user.getUserId()).orElseThrow(() -> new ResourceNotFound("profile not found"));

        }
        String resumeName = "";
        if(!file.isEmpty()){
            resumeName= StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            profile.setResume(resumeName);

        }
        JobSeekerProfile savedProfile = jobSeekerProfileService.saveOrUpdateJobSeekerProfile(profile);
        String uploadDir = "photos/candidates/resume/" + savedProfile.getJobSeekerProfileId();
        try {
            FileUploadUtil.saveFile(uploadDir,resumeName,file);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/dashboard";
    }

    @PreAuthorize("hasAuthority('job seeker')")
    @PostMapping("/add-skill")
    public String addSkill(@RequestParam("skillName") String skillName,
                           @RequestParam("experienceLevel") String experienceLevel,
                           RedirectAttributes redirectAttributes) {
        User currentUser = getAuthenticatedUser();
        JobSeekerProfile profile = jobSeekerProfileService.findJobSeekerProfileById(currentUser.getUserId())
                .orElseThrow(() -> new ResourceNotFound("profile not found"));

        Skill newSkill = new Skill();
        newSkill.setSkillName(skillName);
        newSkill.setExperienceLevel(experienceLevel);
        newSkill.setJobSeekerProfile(profile);

        profile.getSkills().add(newSkill);
        jobSeekerProfileService.saveOrUpdateJobSeekerProfile(profile);

        redirectAttributes.addFlashAttribute("message", "Skill added successfully!");
        return "redirect:/dashboard";
    }

    private User getAuthenticatedUser() {
        String username = userService.getCurrentUser().getEmail();
        return userService.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFound("User not found"));
    }
}