package com.jobportal.service.impl;

import com.jobportal.dto.JobSeekerProfileDTO;
import com.jobportal.entity.JobPostActivity;
import com.jobportal.entity.JobSeekerApply;
import com.jobportal.entity.JobSeekerProfile;
import com.jobportal.entity.User;
import com.jobportal.exception.ResourceNotFound;
import com.jobportal.repository.JobPostActivityRepo;
import com.jobportal.repository.JobSeekerApplyRepo;
import com.jobportal.repository.JobSeekerProfileRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Service
public class JobSeekerApplyService {
    private JobSeekerApplyRepo jobSeekerApplyRepo;
    private JobPostActivityRepo jobPostActivityRepo;
    private UserServiceImpl userService;
    @Autowired
    public JobSeekerApplyService(JobSeekerApplyRepo jobSeekerApplyRepo,
                                 JobPostActivityRepo jobPostActivityRepo, UserServiceImpl userService) {
        this.jobSeekerApplyRepo = jobSeekerApplyRepo;
        this.jobPostActivityRepo = jobPostActivityRepo;
        this.userService = userService;
    }
    public JobSeekerApply applyForAJob(Long jobId , String coverLetter){
        JobSeekerApply jobSeekerApply =new JobSeekerApply();
        JobPostActivity job = jobPostActivityRepo.findJobPostActivityByJobPostId(jobId).
                orElseThrow(()->new ResourceNotFound("Can not find a job with this id "));
        User user = userService.getCurrentUser();
        JobSeekerProfile profile = (JobSeekerProfile) userService.getCurrentUserProfile();
        Boolean alreadyApplied = jobSeekerApplyRepo.existsByJobAndProfile(job, profile);
        if(alreadyApplied){
            throw new IllegalStateException("JobSeekerApply is already applied for this job");
        }
        jobSeekerApply.setProfile(profile);
        jobSeekerApply.setJob(job);
        jobSeekerApply.setCoverLetter(coverLetter);
        jobSeekerApply.setApplyDate(new Date());
        JobSeekerApply savedApplication = jobSeekerApplyRepo.save(jobSeekerApply);
        return savedApplication;
    }

    public Boolean isApplied(JobPostActivity job ,JobSeekerProfile profile){
        Boolean alreadyApplied = jobSeekerApplyRepo.existsByJobAndProfile(job, profile);
        return alreadyApplied;
    }
    public Long countApplied(Long jobId ){
        return jobSeekerApplyRepo.countByJobJobPostId(jobId);
    }
    public List<JobSeekerProfileDTO> findApplicant(Long jobId ){
        return jobSeekerApplyRepo.getNamesByJobPostId(jobId);
    }
}
