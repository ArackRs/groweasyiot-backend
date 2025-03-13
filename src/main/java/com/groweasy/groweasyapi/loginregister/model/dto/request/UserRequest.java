package com.groweasy.groweasyapi.loginregister.model.dto.request;

public record UserRequest(
        String fullName,
        String username,
        String password
) {
}
