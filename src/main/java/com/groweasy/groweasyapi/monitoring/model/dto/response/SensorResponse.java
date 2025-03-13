package com.groweasy.groweasyapi.monitoring.model.dto.response;

import com.groweasy.groweasyapi.monitoring.model.entities.Sensor;

import java.util.List;
import java.util.stream.Collectors;

public record SensorResponse(
        Long id,
        String type,
        String status,
        Long deviceId,
        SensorConfigResponse config
) {
    public static SensorResponse fromEntity(Sensor entity) {
        return new SensorResponse(
                entity.getId(),
                entity.getType().name(),
                entity.getStatus().name(),
                entity.getDevice().getId(),
                SensorConfigResponse.fromEntity(entity.getConfig())
        );
    }

    public static List<SensorResponse> fromEntityList(List<Sensor> entities) {
        return entities.stream()
                .map(SensorResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
