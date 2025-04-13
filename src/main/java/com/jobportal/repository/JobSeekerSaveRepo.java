package com.jobportal.repository;

import com.jobportal.entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekerSaveRepo extends JpaRepository<JobSeekerSave , Long> {
    boolean existsByProfile_User_UserIdAndJob_JobPostId(Long userId, Long jobId);
}
