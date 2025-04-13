package com.jobportal.controller;

import com.jobportal.entity.JobPostActivity;
import com.jobportal.entity.JobSeekerProfile;
import com.jobportal.entity.JobSeekerSave;
import com.jobportal.service.impl.JobPostActivityService;
import com.jobportal.service.impl.JobSeekerApplyService;
import com.jobportal.service.impl.JobSeekerSaveService;
import com.jobportal.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class JobSeekerSaveController {
    private final UserServiceImpl userService;
    private final JobPostActivityService jobPostActivityService;
    private final JobSeekerSaveService jobSeekerSaveService;

    @Autowired
    public JobSeekerSaveController(JobSeekerApplyService jobSeekerApplyService,
                                   UserServiceImpl userService,
                                   JobPostActivityService jobPostActivityService, JobSeekerSaveService jobSeekerSaveService) {
        this.userService = userService;
        this.jobPostActivityService = jobPostActivityService;
        this.jobSeekerSaveService = jobSeekerSaveService;
    }
    @PreAuthorize("hasAuthority('job seeker')")
    @GetMapping("save-job/{id}")
    public String saveJob(@PathVariable("id")Long jobId) {
        JobSeekerSave jobSeekerSave = new JobSeekerSave();
        JobPostActivity job = jobPostActivityService.findJobById(jobId);
        Object object = userService.getCurrentUserProfile();
        if(object instanceof JobSeekerProfile) {
            jobSeekerSave.setProfile((JobSeekerProfile) object);
            jobSeekerSave.setJob(job);
            jobSeekerSaveService.saveJob(jobSeekerSave);
        }
        return "redirect:/dashboard";
    }
}
