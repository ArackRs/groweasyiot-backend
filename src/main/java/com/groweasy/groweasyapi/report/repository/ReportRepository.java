package com.groweasy.groweasyapi.report.repository;

import com.groweasy.groweasyapi.report.model.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByUserId(Long userId);
}
