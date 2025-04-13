package com.jobportal.service.impl;

import com.jobportal.entity.JobSeekerProfile;
import com.jobportal.repository.JobSeekerProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobSeekerProfileService {
    private final JobSeekerProfileRepo jobSeekerProfileRepo;
    @Autowired
    public JobSeekerProfileService(JobSeekerProfileRepo jobSeekerProfileRepo) {
        this.jobSeekerProfileRepo = jobSeekerProfileRepo;
    }
    public Optional<JobSeekerProfile> findJobSeekerProfileById(Long id){
        return jobSeekerProfileRepo.findById(id);
    }

    public JobSeekerProfile saveOrUpdateJobSeekerProfile(JobSeekerProfile profile) {
        jobSeekerProfileRepo.save(profile);
        return profile;
    }

}
