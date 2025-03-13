package com.groweasy.groweasyapi.auth.repository;

import com.groweasy.groweasyapi.auth.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByUsername(String username);
    boolean existsByUsername(String username);
}
