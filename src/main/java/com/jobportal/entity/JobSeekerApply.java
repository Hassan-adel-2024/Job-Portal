package com.jobportal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"profile", "job"})
})
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobSeekerApply implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applyId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_account_id", referencedColumnName = "user_id")
    private JobSeekerProfile profile;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "job_id", referencedColumnName = "jobPostId")
    private JobPostActivity job;

    @Temporal(TemporalType.DATE)  // Ensures only date is stored in DB
    @DateTimeFormat(pattern = "dd-MM-yyyy")  // Useful for Spring MVC input/output formatting
    private Date applyDate;

    @Column(length = 500)  // Helps prevent oversized input
    private String coverLetter;
}
