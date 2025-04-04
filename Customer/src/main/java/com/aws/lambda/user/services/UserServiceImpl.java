package com.aws.lambda.user.services;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.aws.lambda.user.entities.User;
import com.aws.lambda.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for User management operations.
 * Contains business logic and validation for user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Override
    public User createUser(User user) {
        // Validate required fields
        validateUserForCreation(user);
        
        // Set timestamps
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        // Set default values
        if (user.getIsActive() == null) {
            user.setIsActive(true);
        }
        
        return userRepository.save(user);
    }

    @Override
    public User getUser(String uuid) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("User UUID cannot be null or empty");
        }
        return userRepository.findById(uuid);
    }

    @Override
    public User updateUser(User user) {
        if (user.getUuid() == null || user.getUuid().trim().isEmpty()) {
            throw new IllegalArgumentException("User UUID is required for update operation");
        }
        
        // Check if user exists
        User existingUser = userRepository.findById(user.getUuid());
        if (existingUser == null) {
            return null;
        }
        
        // Preserve creation timestamp and set update timestamp
        user.setCreatedAt(existingUser.getCreatedAt());
        user.setUpdatedAt(LocalDateTime.now());
        
        // Preserve isActive if not provided
        if (user.getIsActive() == null) {
            user.setIsActive(existingUser.getIsActive());
        }
        
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(String uuid) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("User UUID cannot be null or empty");
        }
        
        User user = userRepository.findById(uuid);
        if (user == null) {
            return false;
        }
        
        userRepository.delete(uuid);
        return true;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be null or empty");
        }
        return userRepository.findByDepartment(department);
    }

    @Override
    public List<User> getUsersByRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> getActiveUsers() {
        return userRepository.findAll().stream()
                .filter(user -> Boolean.TRUE.equals(user.getIsActive()))
                .collect(Collectors.toList());
    }

    @Override
    public User deactivateUser(String uuid) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("User UUID cannot be null or empty");
        }
        
        User user = userRepository.findById(uuid);
        if (user == null) {
            return null;
        }
        
        user.setIsActive(false);
        user.setUpdatedAt(LocalDateTime.now());
        
        return userRepository.save(user);
    }

    @Override
    public User activateUser(String uuid) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("User UUID cannot be null or empty");
        }
        
        User user = userRepository.findById(uuid);
        if (user == null) {
            return null;
        }
        
        user.setIsActive(true);
        user.setUpdatedAt(LocalDateTime.now());
        
        return userRepository.save(user);
    }

    /**
     * Validate user data for creation.
     * 
     * @param user User object to validate
     */
    private void validateUserForCreation(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("User name is required");
        }
        
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("User email is required");
        }
        
        if (user.getAge() == null || user.getAge() < 18) {
            throw new IllegalArgumentException("User age must be at least 18");
        }
        
        // Check for duplicate email
        List<User> existingUsers = userRepository.findAll();
        boolean emailExists = existingUsers.stream()
                .anyMatch(existingUser -> user.getEmail().equals(existingUser.getEmail()));
        
        if (emailExists) {
            throw new IllegalArgumentException("User with this email already exists");
        }
    }

}
