package com.aws.lambda.user.repositories;

import com.aws.lambda.user.entities.User;
import java.util.List;

/**
 * Repository interface for User data access operations.
 * Defines methods for interacting with the User data store.
 */
public interface UserRepository {

    /**
     * Save a user to the data store.
     * 
     * @param user User object to save
     * @return Saved user object
     */
    User save(User user);

    /**
     * Find a user by their unique identifier.
     * 
     * @param uuid User's unique identifier
     * @return User object if found, null otherwise
     */
    User findById(String uuid);

    /**
     * Find all users in the data store.
     * 
     * @return List of all users
     */
    List<User> findAll();

    /**
     * Delete a user by their unique identifier.
     * 
     * @param uuid User's unique identifier
     */
    void delete(String uuid);

    /**
     * Find users by department.
     * 
     * @param department Department name to search for
     * @return List of users in the specified department
     */
    List<User> findByDepartment(String department);

    /**
     * Find users by role.
     * 
     * @param role Role to search for
     * @return List of users with the specified role
     */
    List<User> findByRole(String role);

    /**
     * Find users by email.
     * 
     * @param email Email address to search for
     * @return User object if found, null otherwise
     */
    User findByEmail(String email);

    /**
     * Find users by name (partial match).
     * 
     * @param name Name to search for (case-insensitive partial match)
     * @return List of users matching the name criteria
     */
    List<User> findByNameContaining(String name);

    /**
     * Check if a user exists by UUID.
     * 
     * @param uuid User's unique identifier
     * @return true if user exists, false otherwise
     */
    boolean existsById(String uuid);

    /**
     * Count total number of users.
     * 
     * @return Total count of users
     */
    long count();

}
