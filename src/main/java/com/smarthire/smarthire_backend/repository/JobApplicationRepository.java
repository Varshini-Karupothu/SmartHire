package com.smarthire.smarthire_backend.repository;

import com.smarthire.smarthire_backend.entity.JobApplication;
import com.smarthire.smarthire_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository
        extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByUser(User user);

}