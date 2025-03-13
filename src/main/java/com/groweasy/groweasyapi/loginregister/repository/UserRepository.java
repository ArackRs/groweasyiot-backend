package com.groweasy.groweasyapi.loginregister.repository;

import com.groweasy.groweasyapi.loginregister.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByUsername(String username);
    boolean existsByUsername(String username);
}
