package com.jobportal.repository;

import com.jobportal.entity.JobPostActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JobPostActivityRepo extends JpaRepository<JobPostActivity,Long> {
    @Query(value = """
    SELECT 
        jpa.job_post_id AS job_post_id,
        jpa.job_title AS job_title,
        jl.id AS locationId,
        jl.city AS city,
        jl.country AS country,
        jc.id AS companyId,
        jc.name AS name
    FROM job_post_activity jpa
    LEFT JOIN job_location jl ON jpa.job_location_id = jl.id
    LEFT JOIN job_company jc ON jpa.job_company_id = jc.id
    WHERE jpa.posted_by_id = :recruiterId
    GROUP BY jpa.job_post_id, jpa.job_title, jl.id, jl.city, jl.country, jc.id, jc.name
    """, nativeQuery = true)

    List<IRecruiterJobs> getRecruiterJobs(@Param("recruiterId") Long recruiterId);
    Optional<JobPostActivity> findJobPostActivityByJobPostId(Long id);
    List<JobPostActivity> findByJobTitleContainingAndJobDescriptionContaining
            (String jobTitle, String jobDescription);
    List<JobPostActivity> findByJobTitleContainingAndJobDescriptionContainingAndJobLocation_CityContainingOrJobLocation_CountryContaining(
            String title, String description, String city, String country);
   List<JobPostActivity> findByJobLocation_CityContainingOrJobLocation_CountryContaining(String city, String country);

    List<JobPostActivity> findByJobTitleContainingAndJobDescriptionContainingAndJobLocation_CityContaining(String title, String description, String city);

    List<JobPostActivity> findByJobTitleContainingAndJobDescriptionContainingAndJobLocation_CountryContaining(String title, String description, String country);
    List<JobPostActivity> findByJobLocation_CityContaining(String city);
}
