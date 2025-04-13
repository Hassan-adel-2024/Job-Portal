package com.jobportal.service.impl;

import com.jobportal.entity.RecruiterProfile;
import com.jobportal.repository.RecruiterProfileRepo;
import com.jobportal.service.RecruiterProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RecruiterProfileServiceImpl implements RecruiterProfileService {
    private final RecruiterProfileRepo recruiterProfileRepo;
    @Autowired
    public RecruiterProfileServiceImpl(RecruiterProfileRepo recruiterProfileRepo) {
        this.recruiterProfileRepo = recruiterProfileRepo;
    }
    public Optional<RecruiterProfile> findRecruiterProfileById(Long id){
       return recruiterProfileRepo.findById(id);
    }

    @Override
    public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
        return recruiterProfileRepo.save(recruiterProfile);
    }
}
