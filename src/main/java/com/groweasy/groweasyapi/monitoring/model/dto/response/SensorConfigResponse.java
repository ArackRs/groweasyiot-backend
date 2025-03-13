package com.groweasy.groweasyapi.monitoring.model.dto.response;

import com.groweasy.groweasyapi.monitoring.model.entities.SensorConfig;

public record SensorConfigResponse(
        Long id,
        Double min,
        Double max,
        Double threshold
) {
    public SensorConfigResponse {
        if (min < 0 || max < 0 || threshold < 0) {
            throw new IllegalArgumentException("Values must be greater than 0");
        }
        if (min > max) {
            throw new IllegalArgumentException("Min values must be less than max values");
        }
        if (threshold > max || threshold < min) {
            throw new IllegalArgumentException("Threshold values must be between min and max values");
        }
    }
    public static SensorConfigResponse fromEntity(SensorConfig config) {
        return new SensorConfigResponse(
                config.getId(),
                config.getMin(),
                config.getMax(),
                config.getThreshold()
        );
    }
}
