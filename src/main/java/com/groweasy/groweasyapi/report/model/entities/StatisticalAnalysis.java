package com.groweasy.groweasyapi.report.model.entities;

import com.groweasy.groweasyapi.monitoring.model.entities.Metric;
import com.groweasy.groweasyapi.monitoring.model.entities.Sensor;
import com.groweasy.groweasyapi.monitoring.model.enums.SensorType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table
public class StatisticalAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String result;

    public void performAnalysis(List<Sensor> sensors) {
        if (sensors.isEmpty()) {
            this.result = "No se encontraron métricas para analizar.";
            return;
        }

        String temperatureAnalysis = analyzeMetric(sensors, SensorType.TEMPERATURE);
        String humidityAnalysis = analyzeMetric(sensors, SensorType.HUMIDITY);
        String lightAnalysis = analyzeMetric(sensors, SensorType.LUMINOSITY);
        String periodAnalysis = analyzePeriod(sensors);

        int count = sensors.stream()
                .map(Sensor::getMetrics)
                .mapToInt(List::size)
                .sum() / 3;

        // Unificar el resultado
        this.result = String.format(
                "%s\n%s\n%s\n%s\nCantidad de registros analizados: %d",
                temperatureAnalysis, humidityAnalysis, lightAnalysis, periodAnalysis, count
        );
    }

    private String analyzeMetric(List<Sensor> sensors, SensorType type) {
        List<Sensor> filteredSensors = sensors.stream()
                .filter(sensor -> sensor.getType() == type)
                .toList();

        List<Metric> metrics = filteredSensors.stream()
                .flatMap(sensor -> sensor.getMetrics().stream())
                .toList();

        if (filteredSensors.isEmpty()) {
            return String.format("No hay datos disponibles para %s.", type.name().toLowerCase());
        }

        double avg = metrics.stream().mapToDouble(Metric::getValue).average().orElse(0);
        double max = metrics.stream().mapToDouble(Metric::getValue).max().orElse(0);
        double min = metrics.stream().mapToDouble(Metric::getValue).min().orElse(0);

        String unit = metrics.getFirst().getUnit();

        return String.format(
                "%s promedio: %.2f %s, Máxima: %.2f %s, Mínima: %.2f %s",
                type.name().toLowerCase(), avg, unit, max, unit, min, unit
        );
    }

    private String analyzePeriod(List<Sensor> sensors) {

        List<Metric> allMetrics = sensors.stream()
                .flatMap(sensor -> sensor.getMetrics().stream())
                .filter(Objects::nonNull)
                .toList();

        // Buscar la fecha mínima y máxima de las métricas
        LocalDateTime startDate = allMetrics.stream()
                .map(Metric::getTimestamp)
                .filter(Objects::nonNull)
                .min(LocalDateTime::compareTo)
                .orElse(null);

        LocalDateTime endDate = allMetrics.stream()
                .map(Metric::getTimestamp)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        if (startDate == null || endDate == null) {
            return "No se encontraron fechas válidas para el análisis.";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formattedStartDate = startDate.format(formatter);
        String formattedEndDate = endDate.format(formatter);

        return String.format("Período de análisis: %s a %s", formattedStartDate, formattedEndDate);
    }

}

