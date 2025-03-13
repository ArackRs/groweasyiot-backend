package com.groweasy.groweasyapi.monitoring.services;

import com.groweasy.groweasyapi.loginregister.model.entities.UserEntity;
import com.groweasy.groweasyapi.loginregister.repository.UserRepository;
import com.groweasy.groweasyapi.monitoring.model.dto.request.DeviceDataRequest;
import com.groweasy.groweasyapi.monitoring.model.dto.response.DeviceConfigResponse;
import com.groweasy.groweasyapi.monitoring.model.entities.DeviceConfig;
import com.groweasy.groweasyapi.monitoring.model.entities.Device;
import com.groweasy.groweasyapi.monitoring.model.entities.Metric;
import com.groweasy.groweasyapi.monitoring.model.entities.Sensor;
import com.groweasy.groweasyapi.monitoring.model.enums.DeviceStatus;
import com.groweasy.groweasyapi.monitoring.model.enums.SensorType;
import com.groweasy.groweasyapi.monitoring.repository.DeviceConfigRepository;
import com.groweasy.groweasyapi.monitoring.repository.DeviceRepository;
import com.groweasy.groweasyapi.monitoring.repository.SensorRepository;
import com.groweasy.groweasyapi.notification.model.enums.NotificationType;
import com.groweasy.groweasyapi.notification.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonitoringService {

    private final DeviceRepository deviceRepository;
    private final DeviceConfigRepository deviceConfigRepository;
    private final SensorRepository sensorRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final SensorService sensorService;

    public void receiveData(DeviceDataRequest data) {

        Device device = deviceRepository.findByMacAddress(data.macAddress())
                .orElseGet(() -> createDefaultDevice(data.macAddress()));

        if (device.getStatus().equals(DeviceStatus.ACTIVE)) {

            Sensor temSensor = sensorService.getByTypeAndDeviceId(SensorType.TEMPERATURE, device.getId());
            Sensor humSensor = sensorService.getByTypeAndDeviceId(SensorType.HUMIDITY, device.getId());
            Sensor lumSensor = sensorService.getByTypeAndDeviceId(SensorType.LUMINOSITY, device.getId());

            temSensor.addMetric(Metric.create(data.temperature(), "°C", temSensor));
            humSensor.addMetric(Metric.create(data.humidity(), "%", humSensor));
            lumSensor.addMetric(Metric.create(data.luminosity(), "lux", lumSensor));

            sensorRepository.saveAll(List.of(temSensor, humSensor, lumSensor));

            checkThresholds(data.temperature(), data.humidity(), data.luminosity(), data.macAddress());
        }
    }


    public DeviceConfigResponse getConfig(String mac) {

        Device device = deviceRepository.findByMacAddress(mac)
                .orElseThrow(() -> new RuntimeException("Device not found"));

        Sensor temSensor = sensorService.getByTypeAndDeviceId(SensorType.TEMPERATURE, device.getId());
        Sensor humSensor = sensorService.getByTypeAndDeviceId(SensorType.HUMIDITY, device.getId());
        Sensor lumSensor = sensorService.getByTypeAndDeviceId(SensorType.LUMINOSITY, device.getId());

        return DeviceConfigResponse.fromSensorConfigs(
                temSensor.getConfig(), humSensor.getConfig(), lumSensor.getConfig()
        );
    }

    private void checkThresholds(Double temperature, Double humidity, Double luminosity, String mac) {

        Device device = deviceRepository.findByMacAddress(mac)
                .orElseThrow(() -> new RuntimeException("Device not found"));

        DeviceConfig config = deviceConfigRepository.findByDeviceId(device.getId())
                .orElseThrow(() -> new RuntimeException("Config not found"));

        Long userId = device.getUser().getId();

        // Comprobación de umbrales de temperatura
        if (temperature < config.getTempMin() || temperature > config.getTempMax()) {
            notificationService.createNotification(userId, "Temp fuera de rango!", NotificationType.ALERT);
        } else if (temperature >= config.getTempThreshold()) {
            notificationService.createNotification(userId, "Alerta Temp alta!", NotificationType.ALERT);
        }

        // Comprobación de umbrales de humedad
        if (humidity < config.getHumMin() || humidity > config.getHumMax()) {
            notificationService.createNotification(userId, "Hum fuera de rango!", NotificationType.ALERT);
        } else if (humidity >= config.getHumThreshold()) {
            notificationService.createNotification(userId, "Alerta Hum alta!", NotificationType.ALERT);
        }

        // Comprobación de umbrales de luminosidad
        if (luminosity < config.getLumMin() || luminosity > config.getLumMax()) {
            notificationService.createNotification(userId, "Lum fuera de rango!", NotificationType.ALERT);
        } else if (luminosity >= config.getLumThreshold()) {
            notificationService.createNotification(userId, "Alerta Lum alta!", NotificationType.ALERT);
        }
    }

    private Device createDefaultDevice(String mac) {

        UserEntity user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("User admin not found"));

        Device device = new Device();
        device.setMacAddress(mac);
        device.setUser(user);
        deviceRepository.save(device);
        sensorRepository.saveAll(device.getSensors());

        return device;
    }
}
