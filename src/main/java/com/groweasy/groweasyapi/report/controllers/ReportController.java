package com.groweasy.groweasyapi.report.controllers;

import com.groweasy.groweasyapi.report.model.dto.ReportResponse;
import com.groweasy.groweasyapi.report.services.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/reports", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Report Controller", description = "API for report operations")
public class ReportController {
    private final ReportService reportService;

    @PostMapping("")
    @Operation(
            summary = "Generate a new report",
            description = "Generates a report based on provided data and analysis type"
    )
    public ResponseEntity<ReportResponse> generateReport() {
        ReportResponse report = reportService.generateReport();
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a report by ID",
            description = "Fetches the report details using its ID"
    )
    public ResponseEntity<ReportResponse> getReportById(@PathVariable Long id) {
        ReportResponse report = reportService.getReportById(id);
        return ResponseEntity.status(HttpStatus.OK).body(report);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a report",
            description = "Deletes a report using its ID"
    )
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/export/{id}")
    @Operation(
            summary = "Export a report",
            description = "Exports a report in the specified format (e.g., PDF, Excel)"
    )
    public ResponseEntity<FileSystemResource> exportReport(
            @PathVariable Long id,
            @RequestParam String format) {
        File reportFile = reportService.exportReport(id, format);
        FileSystemResource resource = new FileSystemResource(reportFile);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + reportFile.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
