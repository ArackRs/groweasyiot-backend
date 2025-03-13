package com.groweasy.groweasyapi.monitoring.repository;

import com.groweasy.groweasyapi.monitoring.model.entities.SensorConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorConfigRepository extends JpaRepository<SensorConfig, Long> {

}
