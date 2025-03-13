package com.groweasy.groweasyapi.monitoring.model.dto.response;

import com.groweasy.groweasyapi.monitoring.model.entities.DeviceConfig;
import com.groweasy.groweasyapi.monitoring.model.entities.SensorConfig;

public record DeviceConfigResponse(
        int sampleInterval,
        double tempMin,
        double tempMax,
        double tempThreshold,
        double humMin,
        double humMax,
        double humThreshold,
        double lumMin,
        double lumMax,
        double lumThreshold
) {
    public DeviceConfigResponse {
        if (sampleInterval < 1) {
            throw new IllegalArgumentException("Sample interval must be greater than 0");
        }
        if (tempMin < 0 || tempMax < 0 || tempThreshold < 0 || humMin < 0 || humMax < 0 || humThreshold < 0 || lumMin < 0 || lumMax < 0 || lumThreshold < 0) {
            throw new IllegalArgumentException("Values must be greater than 0");
        }
        if (tempMin > tempMax || humMin > humMax || lumMin > lumMax) {
            throw new IllegalArgumentException("Min values must be less than max values");
        }
        if (tempThreshold > tempMax || tempThreshold < tempMin || humThreshold > humMax || humThreshold < humMin || lumThreshold > lumMax || lumThreshold < lumMin) {
            throw new IllegalArgumentException("Threshold values must be between min and max values");
        }
    }
    public static DeviceConfigResponse fromEntity(DeviceConfig entity) {
        return new DeviceConfigResponse(
                entity.getSampleInterval(),
                entity.getTempMin(),
                entity.getTempMax(),
                entity.getTempThreshold(),
                entity.getHumMin(),
                entity.getHumMax(),
                entity.getHumThreshold(),
                entity.getLumMin(),
                entity.getLumMax(),
                entity.getLumThreshold()
        );
    }

    public static DeviceConfigResponse fromSensorConfigs(SensorConfig tem, SensorConfig hum, SensorConfig lum) {

        return new DeviceConfigResponse(
                5,
                tem.getMin(),
                tem.getMax(),
                tem.getThreshold(),
                hum.getMin(),
                hum.getMax(),
                hum.getThreshold(),
                lum.getMin(),
                lum.getMax(),
                lum.getThreshold()
        );
    }
}
