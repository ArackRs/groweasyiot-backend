package com.groweasy.groweasyapi.monitoring.model.entities;

import com.groweasy.groweasyapi.monitoring.model.dto.request.SensorConfigRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table
public class DeviceConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int sampleInterval;  // Intervalo de muestreo en segundos
    @Column(nullable = false)
    private double tempMin;  // Mínimo de temperatura permitida
    @Column(nullable = false)
    private double tempMax;  // Máximo de temperatura permitida
    @Column(nullable = false)
    private double tempThreshold;  // Umbral de alerta de temperatura alta
    @Column(nullable = false)
    private double humMin;  // Mínimo de humedad permitida
    @Column(nullable = false)
    private double humMax;  // Máximo de humedad permitida
    @Column(nullable = false)
    private double humThreshold;  // Umbral de alerta de humedad alta
    @Column(nullable = false)
    private int lumMin;  // Mínimo de luminosidad permitida
    @Column(nullable = false)
    private int lumMax;  // Máximo de luminosidad permitida
    @Column(nullable = false)
    private int lumThreshold;  // Umbral de alerta de luminosidad alta

    @OneToOne
    @JoinColumn(nullable = false)
    private Device device;

    public DeviceConfig(Device device) {
        this.device = device;
        initializeDefaults();
    }

    private void initializeDefaults() {
        this.sampleInterval = 10;
        this.tempMin = 15.0;
        this.tempMax = 30.0;
        this.tempThreshold = 28.0;
        this.humMin = 40.0;
        this.humMax = 60.0;
        this.humThreshold = 55.0;
        this.lumMin = 300;
        this.lumMax = 1000;
        this.lumThreshold = 900;
    }

    public static DeviceConfig create(Device device) {

        return DeviceConfig.builder()
                .sampleInterval(10)
                .tempMin(15.0)
                .tempMax(30.0)
                .tempThreshold(28.0)
                .humMin(40.0)
                .humMax(60.0)
                .humThreshold(55.0)
                .lumMin(300)
                .lumMax(1000)
                .lumThreshold(900)
                .device(device)
                .build();
    }

    public void update(SensorConfig config) {

        switch (config.getSensor().getType()) {
            case TEMPERATURE -> {
                this.tempMin = config.getMin();
                this.tempMax = config.getMax();
                this.tempThreshold = config.getThreshold();
            }
            case HUMIDITY -> {
                this.humMin = config.getMin();
                this.humMax = config.getMax();
                this.humThreshold = config.getThreshold();
            }
            case LUMINOSITY -> {
                this.lumMin = (int) config.getMin();
                this.lumMax = (int) config.getMax();
                this.lumThreshold = (int) config.getThreshold();
            }
        }
    }
}
