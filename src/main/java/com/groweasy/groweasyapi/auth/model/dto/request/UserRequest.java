package com.groweasy.groweasyapi.auth.model.dto.request;

public record UserRequest(
        String fullName,
        String username,
        String password
) {
}
