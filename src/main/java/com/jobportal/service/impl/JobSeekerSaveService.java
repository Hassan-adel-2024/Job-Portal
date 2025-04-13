package com.jobportal.service.impl;

import com.jobportal.entity.JobSeekerSave;
import com.jobportal.repository.JobSeekerSaveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobSeekerSaveService {
    private final JobSeekerSaveRepo jobSeekerSaveRepo;
    @Autowired
    public JobSeekerSaveService(JobSeekerSaveRepo jobSeekerSaveRepo) {
        this.jobSeekerSaveRepo = jobSeekerSaveRepo;
    }
    public JobSeekerSave saveJob(JobSeekerSave jobSeekerSave) {
        return jobSeekerSaveRepo.save(jobSeekerSave);
    }
    public Boolean isSaved(Long jobSeekerId , Long jobId) {
        return jobSeekerSaveRepo.existsByProfile_User_UserIdAndJob_JobPostId(jobSeekerId, jobId);
    }
}
