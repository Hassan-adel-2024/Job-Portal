package com.jobportal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
@NoArgsConstructor
@Data
@Entity
@Table(name = "job_seekers_profile")
public class JobSeekerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobSeekerProfileId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private String currentJob;
    private String resume;
    private String university;
    private String major;

    @DateTimeFormat(pattern = "yyyy-MM")
    private Date graduationDate;
    @Column(nullable = true , length = 500)
    private String photosImagePath;
    @OneToMany(targetEntity = Skill.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "jobSeekerProfile")
    private List<Skill> skills;
    public JobSeekerProfile(User user){
        this.user=user;
    }
    @Transient
    public String getPhotosImagePath(){
        if (photosImagePath == null) {
            return "/images/default-profile.png"; // Provide default path
        }
        return "/photos/candidates/" +jobSeekerProfileId+"/"+ photosImagePath;
    }


    public void setResumeFilePath(String filePath) {
    }
}
