package com.jobportal.service.impl;

import com.jobportal.dto.RecruiterJobDto;
import com.jobportal.entity.JobCompany;
import com.jobportal.entity.JobLocation;
import com.jobportal.entity.JobPostActivity;
import com.jobportal.repository.IRecruiterJobs;
import com.jobportal.repository.JobPostActivityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobPostActivityService {
    private final JobPostActivityRepo jobPostActivityRepo;
    @Autowired
    public JobPostActivityService(JobPostActivityRepo jobPostActivityRepo) {
        this.jobPostActivityRepo = jobPostActivityRepo;
    }

    public JobPostActivity addNew(JobPostActivity jobPostActivity){

        return jobPostActivityRepo.save(jobPostActivity);
    }
    public List<RecruiterJobDto> getRecruiterJobs(Long recruiterId){
         List<IRecruiterJobs> recruiterJobs = jobPostActivityRepo.getRecruiterJobs(recruiterId);
         List<RecruiterJobDto> recruiterJobDtos = new ArrayList<>();
         for(IRecruiterJobs rec : recruiterJobs){
             JobLocation jobLocation = new JobLocation(rec.getLocationId(),rec.getCity(),rec.getCountry());
             JobCompany jobCompany = new JobCompany(rec.getCompanyId(),rec.getName(),"");
             recruiterJobDtos.add(new RecruiterJobDto(rec.getTotalCandidates(),rec.getJob_post_id(),rec.getJob_title()
             ,jobCompany,jobLocation));

         }
        return recruiterJobDtos;
    }
    public JobPostActivity findJobById(Long id){
        return jobPostActivityRepo.findJobPostActivityByJobPostId(id).
                orElseThrow(()-> new RuntimeException("Could not fine job with that id"));
    }
    public JobPostActivity update(JobPostActivity job){
        JobPostActivity existingJob= jobPostActivityRepo.findJobPostActivityByJobPostId(job.getJobPostId()).orElseThrow();
        existingJob.setJobTitle(job.getJobTitle());
        existingJob.setJobDescription(job.getJobDescription());
        existingJob.setJobType(job.getJobType());
        existingJob.setJobType(job.getJobType());
        existingJob.setSalary(job.getSalary());
        existingJob.setRemote(job.getRemote());
        existingJob.setJobLocation(job.getJobLocation());
        existingJob.setJobCompany(job.getJobCompany());


       return jobPostActivityRepo.save(existingJob);
    }
    public void deleteJob(JobPostActivity job){
        jobPostActivityRepo.delete(job);
    }
    public List<JobPostActivity> searchJobs(String title, String description, String city, String country) {
        List<JobPostActivity> jobList = new ArrayList<>();

        // Handle null values safely
        title = (title != null) ? title.trim() : "";
        description = (description != null) ? description.trim() : "";
        city = (city != null) ? city.trim() : "";
        country = (country != null) ? country.trim() : "";

        // If all fields are empty, return all jobs
        if (title.isEmpty() && description.isEmpty() && city.isEmpty() && country.isEmpty()) {
            return jobPostActivityRepo.findAll();
        }

        // If city is provided but all other fields are empty, search by city only
        if (!city.isEmpty() && title.isEmpty() && description.isEmpty() && country.isEmpty()) {
            return jobPostActivityRepo.findByJobLocation_CityContaining(city);
        }

        // If city and country are empty, search by title & description only
        if (city.isEmpty() && country.isEmpty()) {
            return jobPostActivityRepo.findByJobTitleContainingAndJobDescriptionContaining(title, description);
        }

        // If title & description are empty, search by city or country only
        if (title.isEmpty() && description.isEmpty()) {
            return jobPostActivityRepo.findByJobLocation_CityContainingOrJobLocation_CountryContaining(city, country);
        }

        // If all fields are present, perform combined search with corrected logic
        List<JobPostActivity> cityResults = jobPostActivityRepo
                .findByJobTitleContainingAndJobDescriptionContainingAndJobLocation_CityContaining(title, description, city);
        List<JobPostActivity> countryResults = jobPostActivityRepo
                .findByJobTitleContainingAndJobDescriptionContainingAndJobLocation_CountryContaining(title, description, country);

        jobList.addAll(cityResults);
        jobList.addAll(countryResults);

        return jobList;
    }
    


}
