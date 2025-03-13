package com.groweasy.groweasyapi.loginregister.controllers;

import com.groweasy.groweasyapi.loginregister.model.dto.request.LoginRequest;
import com.groweasy.groweasyapi.loginregister.model.dto.request.SignupRequest;
import com.groweasy.groweasyapi.loginregister.model.dto.response.AuthResponse;
import com.groweasy.groweasyapi.loginregister.model.dto.response.UserResponse;
import com.groweasy.groweasyapi.loginregister.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Auth Controller", description = "API for authentication operations")
public class AuthController {
    private final AuthService authService;

    @Transactional
    @PostMapping(value = "/sign-up")
    @Operation(
            summary = "Sign up a new user",
            description = "Sign up a new user by providing the user's details"
    )
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.signup(request));
    }

    @Transactional
    @PostMapping(value = "/log-in")
    @Operation(
            summary = "Log in a user",
            description = "Log in a user by providing the user's credentials"
    )
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(request));
    }

    @Transactional
    @PostMapping(value = "/log-out")
    @Operation(
            summary = "Log out a user",
            description = "Log out a user"
    )
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/user")
    @Operation(
            summary = "Get current user",
            description = "Fetches the details of the currently authenticated user"
    )
    public ResponseEntity<UserResponse> getCurrentUser() {
        UserResponse userResponse = UserResponse.fromEntity(authService.getAuthenticatedUser());
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
}
