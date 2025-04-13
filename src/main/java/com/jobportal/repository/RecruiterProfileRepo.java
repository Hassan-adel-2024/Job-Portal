package com.jobportal.repository;

import com.jobportal.entity.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruiterProfileRepo extends JpaRepository<RecruiterProfile,Long> {
}
