package com.groweasy.groweasyapi.monitoring.model.dto.request;

import com.groweasy.groweasyapi.monitoring.model.entities.DeviceConfig;

public record DeviceConfigRequest(
        int sampleInterval,
        double tempMin,
        double tempMax,
        double tempThreshold,
        double humMin,
        double humMax,
        double humThreshold,
        int lumMin,
        int lumMax,
        int lumThreshold
) {
    // Mappear de DTO a entidad
    public DeviceConfig toEntity() {
        return DeviceConfig.builder()
                .sampleInterval(sampleInterval)
                .tempMin(tempMin)
                .tempMax(tempMax)
                .tempThreshold(tempThreshold)
                .humMin(humMin)
                .humMax(humMax)
                .humThreshold(humThreshold)
                .lumMin(lumMin)
                .lumMax(lumMax)
                .lumThreshold(lumThreshold)
                .build();
    }
}
