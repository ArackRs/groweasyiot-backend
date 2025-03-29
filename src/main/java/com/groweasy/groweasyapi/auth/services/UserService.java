package com.groweasy.groweasyapi.auth.services;

import com.groweasy.groweasyapi.auth.model.dto.request.SignupRequest;
import com.groweasy.groweasyapi.auth.model.dto.request.UserRequest;
import com.groweasy.groweasyapi.auth.model.dto.response.UserResponse;
import com.groweasy.groweasyapi.auth.model.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Interface for user management services, providing methods to retrieve, update, and delete user information.
 */
public interface UserService extends UserDetailsService {

    void createUser(SignupRequest signupRequest);

    /**
     * Retrieves a list of all users.
     *
     * @return A {@link List} of {@link UserResponse} objects representing all users.
     */
    List<UserResponse> getAllUsers();

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to be retrieved.
     * @return A {@link UserResponse} object representing the user with the specified username.
     */
    UserEntity getUserByUsername(String username);

    /**
     * Updates the information of a user identified by their username.
     *
     * @param username The {@link String} object containing the new user information.
     */
    void updateUsername(Long userId, String username);

    /**
     * Deletes a user identified by their username.
     *
     */
    void deleteUser(Long userId);

    void updateProfile(Long userId, UserRequest request);
}