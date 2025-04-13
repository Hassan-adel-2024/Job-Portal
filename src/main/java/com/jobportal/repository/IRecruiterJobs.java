package com.jobportal.repository;

public interface IRecruiterJobs {
    Long getTotalCandidates();
    Long getJob_post_id();
    String getJob_title();
    Long getLocationId();
    String getCity();
    String getCountry();
    Long getCompanyId();
    String getName();
}
