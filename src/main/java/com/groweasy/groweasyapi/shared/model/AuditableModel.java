package com.groweasy.groweasyapi.shared.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableModel {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    protected Date createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    protected Date updatedAt;
}
