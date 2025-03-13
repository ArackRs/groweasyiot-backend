package com.groweasy.groweasyapi.notification.model.entities;

import com.groweasy.groweasyapi.notification.model.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String message;
    private NotificationType type;
    private LocalDateTime timestamp;

    // Constructor, getters y setters

    public Notification(Long userId, String message, NotificationType type) {
        this.userId = userId;
        this.message = message;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

}
