package com.groweasy.groweasyapi.monitoring.model.dto.request;

public record DeviceDataRequest(
        String macAddress,
        Double temperature,
        Double humidity,
        Double luminosity
) {
    public DeviceDataRequest {
        if (temperature < -50 || temperature > 50) {
            throw new IllegalArgumentException("Temperature must be between -50 and 50");
        }
        if (humidity < 0 || humidity > 100) {
            throw new IllegalArgumentException("Humidity must be between 0 and 100");
        }
        if (luminosity < 0 || luminosity > 100000) {
            throw new IllegalArgumentException("Luminosity must be between 0 and 100000");
        }
    }
}
