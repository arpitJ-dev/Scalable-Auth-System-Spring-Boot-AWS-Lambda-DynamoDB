package com.aws.lambda.user.services;

import com.aws.lambda.user.entities.User;
import com.aws.lambda.user.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserService implementation.
 * Tests business logic and service layer functionality.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .uuid("test-uuid-123")
                .name("John Doe")
                .email("john.doe@example.com")
                .age(30)
                .department("Engineering")
                .role("Software Engineer")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isActive(true)
                .phoneNumber("+1234567890")
                .build();
    }

    @Test
    void createUser_ValidUser_ReturnsCreatedUser() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        User result = userService.createUser(testUser);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertTrue(result.getIsActive());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_InvalidAge_ThrowsException() {
        testUser.setAge(17); // Below minimum age

        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(testUser);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createUser_DuplicateEmail_ThrowsException() {
        User existingUser = User.builder().email("john.doe@example.com").build();
        when(userRepository.findAll()).thenReturn(Arrays.asList(existingUser));

        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(testUser);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getUser_ValidUuid_ReturnsUser() {
        when(userRepository.findById("test-uuid-123")).thenReturn(testUser);

        User result = userService.getUser("test-uuid-123");

        assertNotNull(result);
        assertEquals("test-uuid-123", result.getUuid());
        assertEquals("John Doe", result.getName());

        verify(userRepository, times(1)).findById("test-uuid-123");
    }

    @Test
    void getUser_InvalidUuid_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.getUser(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            userService.getUser("");
        });

        verify(userRepository, never()).findById(anyString());
    }

    @Test
    void updateUser_ValidUser_ReturnsUpdatedUser() {
        when(userRepository.findById("test-uuid-123")).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User result = userService.updateUser(testUser);

        assertNotNull(result);
        assertNotNull(result.getUpdatedAt());

        verify(userRepository, times(1)).findById("test-uuid-123");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_NonExistentUser_ReturnsNull() {
        when(userRepository.findById("non-existent")).thenReturn(null);

        User result = userService.updateUser(testUser);

        assertNull(result);

        verify(userRepository, times(1)).findById("test-uuid-123");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUser_ValidUuid_ReturnsTrue() {
        when(userRepository.findById("test-uuid-123")).thenReturn(testUser);

        boolean result = userService.deleteUser("test-uuid-123");

        assertTrue(result);

        verify(userRepository, times(1)).findById("test-uuid-123");
        verify(userRepository, times(1)).delete("test-uuid-123");
    }

    @Test
    void deleteUser_NonExistentUser_ReturnsFalse() {
        when(userRepository.findById("non-existent")).thenReturn(null);

        boolean result = userService.deleteUser("non-existent");

        assertFalse(result);

        verify(userRepository, times(1)).findById("non-existent");
        verify(userRepository, never()).delete(anyString());
    }

    @Test
    void getAllUsers_ReturnsUserList() {
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUsersByDepartment_ValidDepartment_ReturnsUserList() {
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findByDepartment("Engineering")).thenReturn(users);

        List<User> result = userService.getUsersByDepartment("Engineering");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Engineering", result.get(0).getDepartment());

        verify(userRepository, times(1)).findByDepartment("Engineering");
    }

    @Test
    void deactivateUser_ValidUuid_ReturnsDeactivatedUser() {
        when(userRepository.findById("test-uuid-123")).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User result = userService.deactivateUser("test-uuid-123");

        assertNotNull(result);
        assertFalse(result.getIsActive());
        assertNotNull(result.getUpdatedAt());

        verify(userRepository, times(1)).findById("test-uuid-123");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void activateUser_ValidUuid_ReturnsActivatedUser() {
        testUser.setIsActive(false);
        when(userRepository.findById("test-uuid-123")).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User result = userService.activateUser("test-uuid-123");

        assertNotNull(result);
        assertTrue(result.getIsActive());
        assertNotNull(result.getUpdatedAt());

        verify(userRepository, times(1)).findById("test-uuid-123");
        verify(userRepository, times(1)).save(any(User.class));
    }

}
