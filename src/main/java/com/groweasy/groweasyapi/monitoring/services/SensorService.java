package com.groweasy.groweasyapi.monitoring.services;

import com.groweasy.groweasyapi.monitoring.model.dto.request.SensorConfigRequest;
import com.groweasy.groweasyapi.monitoring.model.dto.response.MetricResponse;
import com.groweasy.groweasyapi.monitoring.model.entities.*;
import com.groweasy.groweasyapi.monitoring.model.enums.SensorType;
import com.groweasy.groweasyapi.monitoring.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepository;
    private final SensorConfigRepository sensorConfigRepository;

    public void updateConfig(Long id, SensorConfigRequest config) {

        SensorConfig sensorConfig = sensorConfigRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Config not found"));

        sensorConfig.update(config.min(), config.max(), config.threshold());
        sensorConfigRepository.save(sensorConfig);
    }

    public Sensor getByTypeAndDeviceId(SensorType type, Long deviceId) {
        return sensorRepository.findByTypeAndDeviceId(type, deviceId)
                .orElseThrow(() -> new RuntimeException(type.name() + "sensor not found"));
    }

    public List<Sensor> getAll() {
        return sensorRepository.findAll();
    }

    public List<MetricResponse> getMetricsBySensorId(Long sensorId) {

        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new RuntimeException("Sensor not found"));

        return MetricResponse.fromEntityList(sensor.getMetrics());
    }

    public Sensor getSensorById(Long id) {
        return sensorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sensor not found"));
    }
}
