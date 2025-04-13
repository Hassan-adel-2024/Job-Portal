package com.jobportal.repository;

import com.jobportal.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekerProfileRepo extends JpaRepository<JobSeekerProfile,Long> {

}
