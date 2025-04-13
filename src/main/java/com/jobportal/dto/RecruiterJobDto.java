package com.jobportal.dto;

import com.jobportal.entity.JobCompany;
import com.jobportal.entity.JobLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecruiterJobDto {
    private Long totalCandidates;
    private Long jobPostId;
    private String jobTitle;
    private JobCompany jobCompanyId;
    private JobLocation jobLocationId;

}
