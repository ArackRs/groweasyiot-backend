package com.groweasy.groweasyapi.monitoring.model.dto.request;

import com.groweasy.groweasyapi.monitoring.model.entities.SensorConfig;
import com.groweasy.groweasyapi.monitoring.model.enums.SensorType;

public record SensorConfigRequest(
        Double min,
        Double max,
        Double threshold,
        SensorType type
) {
    public SensorConfig toEntity() {
        return SensorConfig.builder()
                .min(min)
                .max(max)
                .threshold(threshold)
                .build();
    }
}
