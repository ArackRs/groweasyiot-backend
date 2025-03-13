package com.groweasy.groweasyapi.loginregister.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignupRequest(
        @NotBlank
        String firstName,
        String lastName,
        @NotBlank
        String username,
        @NotBlank
        String password,
        String role
) {
    public SignupRequest withPasswordEncoded(String password) {
        return new SignupRequest(firstName, lastName, username, password, role);
    }
    public static SignupRequest of(String firstName, String lastName, String username, String password, String role) {
        return new SignupRequest(firstName, lastName, username, password, role);
    }
}