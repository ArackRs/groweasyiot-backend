package com.groweasy.groweasyapi.monitoring.model.entities;

import com.groweasy.groweasyapi.auth.model.entities.UserEntity;
import com.groweasy.groweasyapi.monitoring.model.enums.DeviceStatus;
import com.groweasy.groweasyapi.monitoring.model.enums.SensorType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String macAddress;

    private DeviceStatus status;

    private String location;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Sensor> sensors;

    @OneToOne(mappedBy = "device", cascade = CascadeType.ALL)
    private DeviceConfig deviceConfig;

    @PrePersist
    private void prePersist() {
        if (this.deviceConfig == null) {
            this.deviceConfig = new DeviceConfig(this);
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public Device() {
        location = "Living Room";
        status = DeviceStatus.INACTIVE;
        sensors = new ArrayList<>();
        createSensors();
    }

    public void addSensor(Sensor sensor) {
        this.sensors.add(sensor);
        sensor.setDevice(this);
    }

    public void removeSensor(Sensor sensor) {
        this.sensors.remove(sensor);
        sensor.setDevice(null);
    }

    private void createSensors() {

        Sensor temperature = Sensor.create(SensorType.TEMPERATURE, this);
        Sensor humidity = Sensor.create(SensorType.HUMIDITY, this);
        Sensor luminosity = Sensor.create(SensorType.LUMINOSITY, this);

        sensors.add(temperature);
        sensors.add(humidity);
        sensors.add(luminosity);
    }

    public static Device create(String mac, UserEntity user) {

        return Device.builder()
                .macAddress(mac)
                .location("Living Room")
                .status(DeviceStatus.INACTIVE)
                .user(user)
                .sensors(new ArrayList<>())
                .build();
    }
}
