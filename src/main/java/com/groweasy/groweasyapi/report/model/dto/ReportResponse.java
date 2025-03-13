package com.groweasy.groweasyapi.report.model.dto;

import com.groweasy.groweasyapi.report.model.entities.Report;

import java.time.LocalDate;

public record ReportResponse(
        Long id,
        LocalDate generationDate,
        String data
) {
    public static ReportResponse fromEntity(Report report) {
        return new ReportResponse(
                report.getId(),
                report.getGenerationDate(),
                report.getData()
        );
    }
}
