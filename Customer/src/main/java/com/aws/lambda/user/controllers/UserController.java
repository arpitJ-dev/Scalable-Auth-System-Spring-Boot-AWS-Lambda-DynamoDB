package com.aws.lambda.user.controllers;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.aws.lambda.user.entities.User;
import com.aws.lambda.user.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for User Management operations.
 * Provides CRUD endpoints for user data with comprehensive error handling.
 */
@RestController
@RequestMapping(path = "${user.context.path}")
@EnableWebMvc
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Create a new user in the system.
     * 
     * @param user User object containing user details
     * @return ResponseEntity with created user data
     */
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        try {
            // Set creation timestamp
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setIsActive(true);
            
            User response = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    /**
     * Retrieve a user by their unique identifier.
     * 
     * @param uuid User's unique identifier
     * @return ResponseEntity with user data
     */
    @GetMapping(path = "${user.uuid}")
    public ResponseEntity<User> getUser(@PathVariable("uuid") String uuid) {
        try {
            User response = userService.getUser(uuid);
            if (response == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + uuid);
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    /**
     * Update an existing user's information.
     * 
     * @param user User object with updated information
     * @return ResponseEntity with updated user data
     */
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        try {
            // Set update timestamp
            user.setUpdatedAt(LocalDateTime.now());
            
            User response = userService.updateUser(user);
            if (response == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + user.getUuid());
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    /**
     * Delete a user from the system.
     * 
     * @param uuid User's unique identifier
     * @return ResponseEntity with deletion confirmation
     */
    @DeleteMapping(path = "${user.uuid}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable("uuid") String uuid) {
        try {
            boolean deleted = userService.deleteUser(uuid);
            if (!deleted) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + uuid);
            }
            
            Map<String, String> response = Map.of(
                "message", "User successfully deleted",
                "uuid", uuid,
                "timestamp", LocalDateTime.now().toString()
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    /**
     * Retrieve all users from the system.
     * 
     * @return ResponseEntity with list of all users
     */
    @GetMapping(value = "${user.all}")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> response = userService.getAllUsers();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    /**
     * Search users by department.
     * 
     * @param department Department name to search for
     * @return ResponseEntity with list of users in the department
     */
    @GetMapping(value = "/department/{department}")
    public ResponseEntity<List<User>> getUsersByDepartment(@PathVariable("department") String department) {
        try {
            List<User> response = userService.getUsersByDepartment(department);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    /**
     * Get system health status.
     * 
     * @return ResponseEntity with health information
     */
    @GetMapping(value = "/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> health = Map.of(
            "status", "UP",
            "service", "User Management System",
            "timestamp", LocalDateTime.now().toString(),
            "version", "1.0.0"
        );
        return ResponseEntity.status(HttpStatus.OK).body(health);
    }

}
