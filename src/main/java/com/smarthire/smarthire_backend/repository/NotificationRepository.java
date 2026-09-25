package com.smarthire.smarthire_backend.repository;

import com.smarthire.smarthire_backend.entity.Interview;
import com.smarthire.smarthire_backend.entity.Notification;
import com.smarthire.smarthire_backend.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByUserOrderByCreatedAtDesc(
            User user
    );

    void deleteByInterview(Interview interview);
}