package com.groweasy.groweasyapi.monitoring.controller;

import com.groweasy.groweasyapi.monitoring.model.dto.response.DeviceDataResponse;
import com.groweasy.groweasyapi.monitoring.services.DeviceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/devices")
@Tag(name = "Device Controller", description = "API for device operations")
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping("/{id}")
    public ResponseEntity<Void> registerDevice(@PathVariable Long id) {
        deviceService.connectDevice(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDataResponse> getDevice(@PathVariable Long id) {

        DeviceDataResponse response = DeviceDataResponse.fromEntity(deviceService.getDeviceById(id));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<DeviceDataResponse>> getAllDevices() {

        List<DeviceDataResponse> devices = DeviceDataResponse.fromEntityList(deviceService.getAllDevices());
        return ResponseEntity.status(HttpStatus.OK).body(devices);
    }
}
