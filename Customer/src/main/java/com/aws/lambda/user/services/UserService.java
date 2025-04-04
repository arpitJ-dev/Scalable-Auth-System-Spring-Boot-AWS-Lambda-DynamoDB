package com.aws.lambda.user.services;

import com.aws.lambda.user.entities.User;
import java.util.List;

/**
 * Service interface for User management operations.
 * Defines business logic methods for user-related operations.
 */
public interface UserService {

    /**
     * Create a new user in the system.
     * 
     * @param user User object to be created
     * @return Created user with generated UUID
     */
    User createUser(User user);

    /**
     * Retrieve a user by their unique identifier.
     * 
     * @param uuid User's unique identifier
     * @return User object if found, null otherwise
     */
    User getUser(String uuid);

    /**
     * Update an existing user's information.
     * 
     * @param user User object with updated information
     * @return Updated user object
     */
    User updateUser(User user);

    /**
     * Delete a user from the system.
     * 
     * @param uuid User's unique identifier
     * @return true if user was deleted, false otherwise
     */
    boolean deleteUser(String uuid);

    /**
     * Retrieve all users from the system.
     * 
     * @return List of all users
     */
    List<User> getAllUsers();

    /**
     * Search users by department.
     * 
     * @param department Department name to search for
     * @return List of users in the specified department
     */
    List<User> getUsersByDepartment(String department);

    /**
     * Search users by role.
     * 
     * @param role Role to search for
     * @return List of users with the specified role
     */
    List<User> getUsersByRole(String role);

    /**
     * Get active users only.
     * 
     * @return List of active users
     */
    List<User> getActiveUsers();

    /**
     * Deactivate a user (soft delete).
     * 
     * @param uuid User's unique identifier
     * @return Updated user object with isActive set to false
     */
    User deactivateUser(String uuid);

    /**
     * Activate a user.
     * 
     * @param uuid User's unique identifier
     * @return Updated user object with isActive set to true
     */
    User activateUser(String uuid);

}
