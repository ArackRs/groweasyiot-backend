package com.groweasy.groweasyapi.monitoring.model.dto.response;

import com.groweasy.groweasyapi.monitoring.model.entities.Device;

import java.util.List;

public record DeviceDataResponse(
        Long id,
        String macAddress,
        String status,
        String location,
        List<SensorResponse> sensors
) {
    public static DeviceDataResponse fromEntity(Device device) {
        return new DeviceDataResponse(
                device.getId(),
                device.getMacAddress(),
                device.getStatus().name(),
                device.getLocation(),
                SensorResponse.fromEntityList(device.getSensors())
        );
    }

    public static List<DeviceDataResponse> fromEntityList(List<Device> allDevices) {
        return allDevices.stream()
                .map(DeviceDataResponse::fromEntity)
                .toList();
    }
}