package com.groweasy.groweasyapi.auth.controllers;

import com.groweasy.groweasyapi.auth.facade.AuthenticationFacade;
import com.groweasy.groweasyapi.auth.model.dto.request.UserRequest;
import com.groweasy.groweasyapi.auth.model.dto.response.UserResponse;
import com.groweasy.groweasyapi.auth.model.entities.UserEntity;
import com.groweasy.groweasyapi.auth.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User Controller", description = "API for user operations")
public class UserController {
    private final UserService userService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping(value = "")
    @Operation(
            summary = "Get all users",
            description = "Fetches all users in the system"
    )
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping(value = "/{username}")
    @Operation(
            summary = "Get a user by username",
            description = "Fetches user details by their username"
    )
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {

        UserEntity user = userService.getUserByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(UserResponse.fromEntity(user));
    }

    @PutMapping(value = "/username")
    @Operation(
            summary = "Update username",
            description = "Updates an existing user's details"
    )
    public ResponseEntity<HashMap<String, String>> updateUsername(@RequestParam String username) {

        Long userId = authenticationFacade.getCurrentUser().getId();

        userService.updateUsername(userId, username);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "User updated successfully");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(value = "/me")
    @Operation(
            summary = "Update own account",
            description = "Allows a user to update their own account details"
    )
    public ResponseEntity<HashMap<String, String>> updateOwnAccount(@RequestBody UserRequest request) {

        Long userId = authenticationFacade.getCurrentUser().getId();

        userService.updateProfile(userId, request);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "User updated successfully");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/me")
    @Operation(
            summary = "Delete own account",
            description = "Allows a user to delete their own account"
    )
    public ResponseEntity<Void> deleteAccount(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
