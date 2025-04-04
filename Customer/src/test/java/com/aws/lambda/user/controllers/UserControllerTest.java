package com.aws.lambda.user.controllers;

import com.aws.lambda.user.entities.User;
import com.aws.lambda.user.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for UserController.
 * Tests REST API endpoints and error handling scenarios.
 */
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void createUser_ValidUser_ReturnsCreatedUser() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(testUser);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid").value("test-uuid-123"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void getUser_ValidUuid_ReturnsUser() throws Exception {
        when(userService.getUser("test-uuid-123")).thenReturn(testUser);

        mockMvc.perform(get("/user/test-uuid-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value("test-uuid-123"))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void getUser_NonExistentUuid_ReturnsNotFound() throws Exception {
        when(userService.getUser("non-existent")).thenReturn(null);

        mockMvc.perform(get("/user/non-existent"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_ValidUser_ReturnsUpdatedUser() throws Exception {
        when(userService.updateUser(any(User.class))).thenReturn(testUser);

        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value("test-uuid-123"));
    }

    @Test
    void deleteUser_ValidUuid_ReturnsSuccess() throws Exception {
        when(userService.deleteUser("test-uuid-123")).thenReturn(true);

        mockMvc.perform(delete("/user/test-uuid-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User successfully deleted"));
    }

    @Test
    void getAllUsers_ReturnsUserList() throws Exception {
        List<User> users = Arrays.asList(testUser);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].uuid").value("test-uuid-123"));
    }

    @Test
    void getUsersByDepartment_ValidDepartment_ReturnsUserList() throws Exception {
        List<User> users = Arrays.asList(testUser);
        when(userService.getUsersByDepartment("Engineering")).thenReturn(users);

        mockMvc.perform(get("/user/department/Engineering"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].department").value("Engineering"));
    }

    @Test
    void healthCheck_ReturnsHealthStatus() throws Exception {
        mockMvc.perform(get("/user/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("User Management System"));
    }

}
