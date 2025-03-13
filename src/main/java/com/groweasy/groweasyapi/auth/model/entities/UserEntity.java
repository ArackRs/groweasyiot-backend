package com.groweasy.groweasyapi.auth.model.entities;

import com.groweasy.groweasyapi.monitoring.model.entities.Device;
import com.groweasy.groweasyapi.report.model.entities.Report;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table
@EntityListeners(AuditingEntityListener.class)
public class UserEntity extends AbstractAggregateRoot<UserEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @ManyToMany(targetEntity = RoleEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Device> deviceList = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Report report;

//    public void setDeviceConfig(DeviceConfig deviceConfig) {
//        this.deviceConfig = deviceConfig;
//        deviceConfig.setUser(this);
//    }

    public void setReport(Report report) {
        this.report = report;
        report.setUser(this);
    }

//    public void setDeviceDataList(List<DeviceData> deviceDataList) {
//        this.deviceDataList = deviceDataList;
//        deviceDataList.forEach(deviceData -> deviceData.setUser(this));
//    }
}