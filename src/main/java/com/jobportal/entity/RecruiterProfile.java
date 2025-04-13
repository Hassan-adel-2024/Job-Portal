package com.jobportal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
@Entity
@Table(name = "recruiters_profile")
public class RecruiterProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserAccountId;
    @OneToOne
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private String company;
    @Column(nullable = true , length = 500)
    private String profilePhotoUrl;
    public RecruiterProfile(User user){
        this.user=user;

    }
    @Transient
    public String getPhotosImagePath(){
        if (profilePhotoUrl==null) return null;
        return "/photos/recruiters/"+UserAccountId+"/"+profilePhotoUrl;

    }


}
