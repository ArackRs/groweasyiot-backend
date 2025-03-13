package com.groweasy.groweasyapi.loginregister.model.dto.response;

public record AuthResponse(
        String username,
        String message,
        Boolean status,
        String jwt) {
}