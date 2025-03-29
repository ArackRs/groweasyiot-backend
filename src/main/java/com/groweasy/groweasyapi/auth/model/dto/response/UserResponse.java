package com.groweasy.groweasyapi.auth.model.dto.response;

import com.groweasy.groweasyapi.auth.model.entities.UserEntity;

import java.util.List;

public record UserResponse (
        Long id,
        String fullName,
        String username,
        List<String> role
) {
    public static UserResponse fromEntity(UserEntity user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getUsername(),
                user.getRoles().stream().map(role -> role.getRoleName().name()).toList()
        );
    }

    public static UserEntity toEntity(UserResponse user) {

        return UserEntity.builder()
                .id(user.id())
                .fullName(user.fullName())
                .username(user.username())
                .build();
    }
}