package com.jobportal.repository;

import com.jobportal.entity.JobPostActivity;
import com.jobportal.entity.JobSeekerApply;
import com.jobportal.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jobportal.dto.JobSeekerProfileDTO;

import java.util.List;

public interface JobSeekerApplyRepo extends JpaRepository<JobSeekerApply,Long> {
    Long countByJob_JobPostId(Long jobPostId);
    boolean existsByJobAndProfile(JobPostActivity job, JobSeekerProfile profile);
    Long countByJobJobPostId(Long jobPostId);

    @Query("SELECT new com.jobportal.dto.JobSeekerProfileDTO(j.profile.firstName, j.profile.lastName,j.profile.jobSeekerProfileId) " +
            "FROM JobSeekerApply j WHERE j.job.jobPostId = :jobPostId")
    List<JobSeekerProfileDTO> getNamesByJobPostId(@Param("jobPostId") Long jobPostId);





}


