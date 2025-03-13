package com.groweasy.groweasyapi.monitoring.model.dto.response;

import com.groweasy.groweasyapi.monitoring.model.entities.Metric;

import java.util.List;
import java.util.stream.Collectors;

public record MetricResponse(
        String metric,
        String timestamp
) {
    public static MetricResponse fromEntity(Metric metric) {
        return new MetricResponse(
                metric.getValue() + " " + metric.getUnit(),
                metric.getTimestamp().toString()
        );
    }

    public static List<MetricResponse> fromEntityList(List<Metric> metrics) {
        return metrics.stream()
                .map(MetricResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
