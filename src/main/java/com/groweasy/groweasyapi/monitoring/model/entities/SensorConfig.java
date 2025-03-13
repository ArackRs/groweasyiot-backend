package com.groweasy.groweasyapi.monitoring.model.entities;

import com.groweasy.groweasyapi.monitoring.model.dto.response.SensorResponse;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table
public class SensorConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private double min;
    @Column(nullable = false)
    private double max;
    @Column(nullable = false)
    private double threshold;

    @OneToOne
    @JoinColumn(nullable = false)
    private Sensor sensor;

    public SensorConfig(Sensor sensor) {
        this.sensor = sensor;
        sensor.setConfig(this);
        initializeDefaults();
    }

    public void update(double min, double max, double threshold) {
        this.min = min;
        this.max = max;
        this.threshold = threshold;
    }

    private void initializeDefaults() {

        switch (this.sensor.getType()) {
            case TEMPERATURE:
                this.min = 15.0;
                this.max = 30.0;
                this.threshold = 28.0;
                break;
            case HUMIDITY:
                this.min = 40.0;
                this.max = 60.0;
                this.threshold = 55.0;
                break;
            case LUMINOSITY:
                this.min = 300;
                this.max = 1000;
                this.threshold = 900;
                break;
        }
    }
}
