package com.groweasy.groweasyapi.monitoring.services;

import com.groweasy.groweasyapi.auth.facade.AuthenticationFacade;
import com.groweasy.groweasyapi.auth.model.entities.UserEntity;
import com.groweasy.groweasyapi.monitoring.model.entities.*;
import com.groweasy.groweasyapi.monitoring.model.enums.DeviceStatus;
import com.groweasy.groweasyapi.monitoring.repository.DeviceConfigRepository;
import com.groweasy.groweasyapi.monitoring.repository.DeviceRepository;
import com.groweasy.groweasyapi.monitoring.repository.MetricRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceConfigRepository deviceConfigRepository;
    private final MetricRepository metricRepository;
    private final AuthenticationFacade authenticationFacade;

    public void connectDevice(Long id) {

        UserEntity user = authenticationFacade.getCurrentUser();

        Device device = getDeviceById(id);
        device.setUser(user);
        device.setStatus(DeviceStatus.ACTIVE);

        deviceRepository.save(device);
    }

    public Device getDeviceById(Long id) {

        return deviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found"));
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }
}
