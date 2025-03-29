package com.groweasy.groweasyapi.auth.handler;

import com.groweasy.groweasyapi.auth.model.entities.PermissionEntity;
import com.groweasy.groweasyapi.auth.model.entities.RoleEntity;
import com.groweasy.groweasyapi.auth.model.entities.UserEntity;
import com.groweasy.groweasyapi.auth.model.enums.RoleEnum;
import com.groweasy.groweasyapi.auth.repository.RoleRepository;
import com.groweasy.groweasyapi.auth.repository.UserRepository;
import com.groweasy.groweasyapi.monitoring.repository.DeviceConfigRepository;
import com.groweasy.groweasyapi.monitoring.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class SeedingEventHandler {
    private final UserRepository userPersistence;
    private final RoleRepository rolePersistence;
    private final PasswordEncoder passwordEncoder;
    private final DeviceRepository deviceRepository;
    private final DeviceConfigRepository deviceConfigRepository;

    @EventListener
    public void on(ApplicationReadyEvent event) {
        var name = event.getApplicationContext().getId();
        log.info("Starting to seed roles and users for {} at {}", name, new Timestamp(System.currentTimeMillis()));

        PermissionEntity createPermission = PermissionEntity.builder().name("CREATE").build();
        PermissionEntity readPermission = PermissionEntity.builder().name("READ").build();
        PermissionEntity updatePermission = PermissionEntity.builder().name("UPDATE").build();
        PermissionEntity deletePermission = PermissionEntity.builder().name("DELETE").build();

        RoleEntity roleAdmin = RoleEntity.builder()
                .roleName(RoleEnum.ADMIN)
                .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
                .build();

        RoleEntity roleUser = RoleEntity.builder()
                .roleName(RoleEnum.AMATEUR)
                .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
                .build();

        RoleEntity roleGuest = RoleEntity.builder()
                .roleName(RoleEnum.EXPERT)
                .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
                .build();

        rolePersistence.saveAll(Set.of(roleAdmin, roleUser, roleGuest));

        //USUARIO DE PRUEBA
        seedUsers(roleAdmin);

        log.info("Finished seeding roles and users for {} at {}", name, new Timestamp(System.currentTimeMillis()));
    }

    private void seedUsers(RoleEntity roleAdmin) {

        // This is the user that will be created during the seeding process
        UserEntity user = UserEntity.builder()
                .fullName("Jack Arana")
                .username("arack")
                .password(passwordEncoder.encode("123"))
                .roles(Set.of(roleAdmin))
                .build();

        userPersistence.save(user);

//        seedDevice(user);
    }

//    private void seedDevice(UserEntity user) {
//
//        DeviceData deviceData = DeviceData.builder()
//                .serialNumber("device-ge001")
//                .location("Living Room")
//                .status(DeviceStatus.ACTIVE)
//                .user(user)
//                .build();
//
//        deviceDataRepository.save(deviceData);
//
//        seedConfig(deviceData);
//    }
//
//    private void seedConfig(DeviceData deviceData) {
//
//        DeviceConfig deviceConfig = DeviceConfig.create(deviceData);
//        deviceConfigRepository.save(deviceConfig);
//    }
}