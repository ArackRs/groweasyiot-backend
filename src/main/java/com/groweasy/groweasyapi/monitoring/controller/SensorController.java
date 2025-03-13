package com.groweasy.groweasyapi.monitoring.controller;

import com.groweasy.groweasyapi.monitoring.model.dto.request.SensorConfigRequest;
import com.groweasy.groweasyapi.monitoring.model.dto.response.MetricResponse;
import com.groweasy.groweasyapi.monitoring.model.dto.response.SensorResponse;
import com.groweasy.groweasyapi.monitoring.services.SensorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/sensors")
@Tag(name = "Sensor Controller", description = "API for sensor operations")
public class SensorController {

    private final SensorService sensorService;

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateById(@PathVariable Long id, @RequestBody SensorConfigRequest config) {
        sensorService.updateConfig(id, config);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(SensorResponse.fromEntity(sensorService.getSensorById(id)));
    }


    @GetMapping
    public ResponseEntity<List<SensorResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(SensorResponse.fromEntityList(sensorService.getAll()));
    }

    @GetMapping("/{id}/metrics")
    public ResponseEntity<List<MetricResponse>> getMetricsById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(sensorService.getMetricsBySensorId(id));
    }
}
