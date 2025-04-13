    package com.jobportal.entity;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.hibernate.validator.constraints.Length;
    import org.springframework.format.annotation.DateTimeFormat;

    import java.util.Date;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Entity
    public class JobPostActivity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long jobPostId;
        @ManyToOne
        @JoinColumn(name = "postedById" , referencedColumnName = "userId")
        private User postedBy;
        @ManyToOne(cascade = CascadeType.PERSIST)
        @JoinColumn(name = "jobLocationId" , referencedColumnName = "id")
        private JobLocation jobLocation;
        @ManyToOne(cascade = CascadeType.PERSIST)
        @JoinColumn(name = "jobCompanyId",referencedColumnName = "id")
        private JobCompany jobCompany;
        @Transient
        private Boolean isActive;
        @Transient
        private Boolean isSaved;
        @Length(max = 10000)
        private String jobDescription;
        private String jobType;
        private String salary;
        private String remote;
        @Temporal(TemporalType.TIMESTAMP)
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // Works if binding request parameters
        private Date postedDate;
        private String jobTitle;

    }
