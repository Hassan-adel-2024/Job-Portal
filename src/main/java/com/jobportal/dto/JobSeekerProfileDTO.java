package com.jobportal.dto;

import com.jobportal.repository.IJobSeekerProfile;

public class JobSeekerProfileDTO implements IJobSeekerProfile {
    private Long id;
    private String firstName;
    private String lastName;

    public JobSeekerProfileDTO() {
    }

    public JobSeekerProfileDTO(String firstName, String lastName , Long id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

}
