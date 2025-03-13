package com.groweasy.groweasyapi.report.services;

import com.groweasy.groweasyapi.loginregister.model.entities.UserEntity;
import com.groweasy.groweasyapi.loginregister.services.AuthService;
import com.groweasy.groweasyapi.monitoring.model.entities.Device;
import com.groweasy.groweasyapi.monitoring.model.entities.Sensor;
import com.groweasy.groweasyapi.monitoring.repository.DeviceRepository;
import com.groweasy.groweasyapi.monitoring.repository.SensorRepository;
import com.groweasy.groweasyapi.report.model.dto.ReportResponse;
import com.groweasy.groweasyapi.report.model.entities.Report;
import com.groweasy.groweasyapi.report.model.entities.StatisticalAnalysis;
import com.groweasy.groweasyapi.report.model.enums.RecommendationEnum;
import com.groweasy.groweasyapi.report.repository.ReportRepository;
import com.groweasy.groweasyapi.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final AuthService authService;
    private final ReportRepository reportRepository;
    private final DeviceRepository deviceRepository;
    private final SensorRepository sensorRepository;

    @Override
    public ReportResponse generateReport() {

        UserEntity user = authService.getAuthenticatedUser();
        Device device = deviceRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No data found for the user"));

        List<Sensor> sensors = sensorRepository.findAllByDeviceId(device.getId());

        StatisticalAnalysis analysis = new StatisticalAnalysis();
        analysis.performAnalysis(sensors);

        Report report = reportRepository.findByUserId(user.getId())
                .orElseGet(() -> Report.builder()
                        .generationDate(LocalDate.now())
                        .data(analysis.getResult())
                        .recommendation(RecommendationEnum.LOW)
                        .statisticalAnalysis(analysis)
                        .user(user)
                        .build());

        Report newReport = updateReport(report, analysis);

        return ReportResponse.fromEntity(newReport);
    }

    private Report updateReport(Report report, StatisticalAnalysis analysis) {
        report.setGenerationDate(LocalDate.now());
        report.setData(analysis.getResult());
        report.setRecommendation(RecommendationEnum.LOW);
        report.setStatisticalAnalysis(analysis);

        return reportRepository.save(report);
    }

    @Override
    public ReportResponse getReportById(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found"));

        return ReportResponse.fromEntity(report);
    }

    @Override
    public void deleteReport(Long reportId) {
        reportRepository.deleteById(reportId);
    }

    @Override
    public File exportReport(Long reportId, String format) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found"));

        return exportToFile(report, format);
    }

    private File exportToFile(Report report, String format) {
        // Implementar la exportación a PDF.
        return null;
    }


    //    private void generatePDF(String filePath) throws FileNotFoundException, DocumentException {
//        Document document = new Document();
//        PdfWriter.getInstance(document, new FileOutputStream(filePath));
//        document.open();
//        document.add(new com.itextpdf.text.Paragraph("Reporte de Datos del Dispositivo"));
//        // Agregar más contenido al PDF aquí...
//        document.close();
//    }
}
