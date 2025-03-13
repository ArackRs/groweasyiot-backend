package com.groweasy.groweasyapi.report.services;

import com.groweasy.groweasyapi.report.model.dto.ReportResponse;

import java.io.File;

public interface ReportService {

    ReportResponse generateReport();
    ReportResponse getReportById(Long reportId);
    void deleteReport(Long reportId);
    File exportReport(Long reportId, String format);
}
