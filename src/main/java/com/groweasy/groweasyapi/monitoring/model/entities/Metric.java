package com.groweasy.groweasyapi.monitoring.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "metric_value")
    private Double value;

    @Column
    private String unit;

    @Column
    private LocalDateTime timestamp = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(nullable = false)
    private Sensor sensor;

    public static Metric create(Double value, String unit, Sensor sensor) {
        return Metric.builder()
                .value(value)
                .unit(unit)
                .timestamp(LocalDateTime.now())
                .sensor(sensor)
                .build();
    }
}
