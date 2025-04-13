package com.jobportal.controller;

import com.jobportal.service.impl.JobSeekerApplyService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@NoArgsConstructor
public class JobSeekerApplyController {

    private JobSeekerApplyService jobSeekerApplyService;

    @Autowired
    public JobSeekerApplyController(JobSeekerApplyService jobSeekerApplyService) {
        this.jobSeekerApplyService = jobSeekerApplyService;
    }

    // Show application form (secured for job seekers)
    @PreAuthorize("hasRole('job seeker')")
    @GetMapping("/apply/{jobId}")
    public String showApplicationForm(
            @PathVariable("jobId") Long jobId,
            Model model
    ) {
        model.addAttribute("jobId", jobId);
        model.addAttribute("coverLetter", ""); // Initialize empty cover letter
        return "ApplyJobForm"; // Thymeleaf template
    }

    // Submit application
    @PreAuthorize("hasRole('job seeker')")
    @PostMapping("/submitApplication")
    public String submitApplication(
            @RequestParam("jobId") Long jobId,
            @RequestParam("coverLetter") String coverLetter,
            RedirectAttributes redirectAttributes

    ) {

        try {
            // Save application (replace with your service logic)
            jobSeekerApplyService.applyForAJob(jobId, coverLetter);

            // Success message
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Application submitted successfully!"
            );
            return "redirect:/dashboard"; // Redirect to job listings

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Failed to apply: " + e.getMessage()
            );
            return "redirect:/apply/" + jobId; // Retry form
        }
    }
}