package com.jobportal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(uniqueConstraints = {
       @UniqueConstraint (columnNames = {"user_account_id","job_id"})
})
public class JobSeekerSave implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_account_id" , referencedColumnName = "user_id")
    private JobSeekerProfile profile;
    @ManyToOne
    @JoinColumn(name="job_id" , referencedColumnName = "jobPostId")
    private JobPostActivity job ;
}
