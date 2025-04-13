package com.jobportal.controller;

import com.jobportal.dto.JobSeekerProfileDTO;
import com.jobportal.dto.RecruiterJobDto;
import com.jobportal.entity.JobPostActivity;
import com.jobportal.entity.JobSeekerProfile;
import com.jobportal.entity.RecruiterProfile;
import com.jobportal.entity.User;
import com.jobportal.exception.ResourceNotFound;
import com.jobportal.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class JobPostActivityController {
    private final UserServiceImpl userService;
    private final JobPostActivityService jobPostActivityService;
    private final JobSeekerApplyService jobSeekerApplyService;
    private final JobSeekerSaveService jobSeekerSaveService;
    private final JobSeekerProfileService jobSeekerProfileService;
    @Autowired
    public JobPostActivityController(UserServiceImpl userService,
                                     JobPostActivityService jobPostActivityService,
                                     JobSeekerApplyService jobSeekerApplyService,
                                     JobSeekerSaveService jobSeekerSaveService,
                                     JobSeekerProfileService jobSeekerProfileService) {
        this.userService = userService;
        this.jobPostActivityService = jobPostActivityService;
        this.jobSeekerApplyService = jobSeekerApplyService;
        this.jobSeekerSaveService = jobSeekerSaveService;
        this.jobSeekerProfileService = jobSeekerProfileService;
    }




    @GetMapping("/dashboard")
    public String SearchJobs(Model model, @RequestParam(required = false) String jobTitle,
                             @RequestParam(required = false) String jobDescription,
                             @RequestParam(required = false) String city,
                             @RequestParam(required = false) String country) {
        Object currentUserProfile = userService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            model.addAttribute("username", currentUserName);
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("recruiter"))) {
                List<RecruiterJobDto> recruiterJobDtoList = jobPostActivityService.
                        getRecruiterJobs(((RecruiterProfile) currentUserProfile).getUserAccountId());
                model.addAttribute("jobPost", recruiterJobDtoList);

            }
        }
        JobSeekerProfile seekerProfile = new JobSeekerProfile();


        model.addAttribute("jobTitle", jobTitle);
        model.addAttribute("jobDescription", jobDescription);
        model.addAttribute("city", city);
        model.addAttribute("country", country);
        model.addAttribute("user", currentUserProfile);

        List<JobPostActivity> jobList = jobPostActivityService.searchJobs(jobTitle, jobDescription, city, country);
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("job seeker"))) {
            seekerProfile = (JobSeekerProfile) userService.getCurrentUserProfile();
            HashMap<Long, Boolean> isAppliedMap = new HashMap<>();
            HashMap<Long, Boolean> isSavedMap = new HashMap<>();
            for (JobPostActivity job : jobList) {
                isAppliedMap.put(job.getJobPostId(), jobSeekerApplyService.isApplied(job, seekerProfile));
                isSavedMap.put(job.getJobPostId(), jobSeekerSaveService.
                        isSaved(seekerProfile.getJobSeekerProfileId(), job.getJobPostId()));
            }
            model.addAttribute("seekerProfile", seekerProfile);
            model.addAttribute("appliedJobsMap", isAppliedMap);
            model.addAttribute("savedJobsMap", isSavedMap);

        }
        model.addAttribute("jobs", jobList);
        System.out.println("Current User Profile: ===========> " + currentUserProfile.getClass().getName());
        return "dashboard";
    }

    @GetMapping("/dashboard/add")
    public String addJob(Model model) {
        model.addAttribute("job", new JobPostActivity());
        model.addAttribute("user", userService.getCurrentUserProfile());
        return "add-job";
    }

    @PostMapping("/dashboard/addNew")
    public String addNew(@ModelAttribute JobPostActivity jobPostActivity,
                         Model model, @RequestParam("companyLogo") MultipartFile file) {
        User user = userService.getCurrentUser();
        if (user != null) {
            jobPostActivity.setPostedBy(user);
        }
        jobPostActivity.setPostedDate(new Date());
        model.addAttribute("job", jobPostActivity);
        jobPostActivity.setIsActive(true);
        jobPostActivity.setIsSaved(true);
        jobPostActivityService.addNew(jobPostActivity);
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard/edit/{id}")
    public String showEditForm(Model model, @PathVariable("id") Long jobId) {
        JobPostActivity job = jobPostActivityService.findJobById(jobId);
        model.addAttribute("job", job);
        return "edit-job";
    }

    @PostMapping("/dashboard/jobs/update")
    public String editJob(@ModelAttribute JobPostActivity job) {
        jobPostActivityService.update(job);
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard/delete/{id}")
    public String delete(@PathVariable("id") Long postId) {
        JobPostActivity job = jobPostActivityService.findJobById(postId);
        jobPostActivityService.deleteJob(job);
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard/view/{id}")
    public String viewJob(@PathVariable("id") Long postId, Model model) {

        JobPostActivity job = jobPostActivityService.findJobById(postId);
        Long applicantCount = jobSeekerApplyService.countApplied(postId);
        model.addAttribute("applicantCount", applicantCount);
        model.addAttribute("job", job);
        return "view-job";

    }
    @PreAuthorize("hasAuthority('recruiter')")
    @GetMapping("/job/{id}/applicants")
    public String getApplicants(@PathVariable("id") Long postId, Model model) {
        JobPostActivity job = jobPostActivityService.findJobById(postId);
        List<JobSeekerProfileDTO> applicants = jobSeekerApplyService.findApplicant(postId);
        System.out.println(applicants);
        model.addAttribute("applicants", applicants);
        model.addAttribute("job", job);
        return "applicants";

    }

    @GetMapping("/applicants/applicant-profile/{applicantId}")
    public String viewApplicantPeofile(Model model, @PathVariable Long applicantId) {
        JobSeekerProfile profile = jobSeekerProfileService.findJobSeekerProfileById(applicantId).
                orElseThrow(()-> new ResourceNotFound("profile not found"));
        model.addAttribute("profile", profile);
        return "applicant-profile";
    }

}
