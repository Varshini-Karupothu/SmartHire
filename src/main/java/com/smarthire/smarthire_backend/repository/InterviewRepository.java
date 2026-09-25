package com.smarthire.smarthire_backend.repository;

import com.smarthire.smarthire_backend.entity.Interview;
import com.smarthire.smarthire_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    List<Interview> findByUser(User user);

}