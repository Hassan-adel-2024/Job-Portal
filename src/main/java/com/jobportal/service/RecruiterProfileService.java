package com.jobportal.service;

import com.jobportal.entity.RecruiterProfile;

import java.util.Optional;

public interface RecruiterProfileService {
    Optional<RecruiterProfile> findRecruiterProfileById(Long id);

    RecruiterProfile addNew(RecruiterProfile recruiterProfile);
}
