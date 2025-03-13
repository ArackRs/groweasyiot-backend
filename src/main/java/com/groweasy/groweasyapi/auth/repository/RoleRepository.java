package com.groweasy.groweasyapi.auth.repository;

import com.groweasy.groweasyapi.auth.model.entities.RoleEntity;
import com.groweasy.groweasyapi.auth.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Set<RoleEntity> findRoleEntitiesByRoleNameIn(List<RoleEnum> roleList);
    Optional<RoleEntity> findByRoleName(RoleEnum roleName);
}
