package com.groweasy.groweasyapi.monitoring.model.entities;

import com.groweasy.groweasyapi.monitoring.model.enums.DeviceStatus;
import com.groweasy.groweasyapi.monitoring.model.enums.SensorType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private SensorType type;

    @Column
    @Enumerated(EnumType.STRING)
    private DeviceStatus status;

    @OneToOne(mappedBy = "sensor", cascade = CascadeType.ALL)
    private SensorConfig config;

    @PrePersist
    private void prePersist() {
        if (this.config == null) {
            this.config = new SensorConfig(this);
        }
    }

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Metric> metrics = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "device_data_id")
    private Device device;

    public void addMetric(Metric metric) {
        this.metrics.add(metric);
        metric.setSensor(this);
    }

    public void removeMetric(Metric metric) {
        this.metrics.remove(metric);
        metric.setSensor(null);
    }

    public static Sensor create(SensorType sensorType, Device device) {
        return Sensor.builder()
                .type(sensorType)
                .status(DeviceStatus.ACTIVE)
                .metrics(new ArrayList<>())
                .device(device)
                .build();
    }

//    @PostLoad // Método que se ejecuta después de cargar la entidad
//    public void init() {
//        if (this.timestamp == null) {
//            this.timestamp = LocalDateTime.now();
//        }
//    }
}
