package com.groweasy.groweasyapi.notification.repository;

import com.groweasy.groweasyapi.notification.model.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
}